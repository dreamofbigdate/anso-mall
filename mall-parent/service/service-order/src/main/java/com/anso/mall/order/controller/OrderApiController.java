package com.anso.mall.order.controller;

import com.anso.mall.cart.CartFeignClient;
import com.anso.mall.common.constant.RedisConst;
import com.anso.mall.common.result.Result;
import com.anso.mall.common.util.AuthContextHolder;
import com.anso.mall.model.cart.CartInfo;
import com.anso.mall.model.order.OrderDetail;
import com.anso.mall.model.order.OrderInfo;
import com.anso.mall.model.user.UserAddress;
import com.anso.mall.order.service.OrderService;
import com.anso.mall.product.client.ProductFeignClient;
import com.anso.mall.user.client.UserFeignClient;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 19:49
 */
@RestController
@RequestMapping("api/order")
public class OrderApiController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 确认订单
     *
     * @param request
     * @return
     */
    @GetMapping("auth/trade")
    public Result<Map<String, Object>> trade(HttpServletRequest request) {
        // 获取到用户Id
        String userId = AuthContextHolder.getUserId(request);

        //获取用户地址
        List<UserAddress> userAddressList = userFeignClient.findUserAddressListByUserId(userId);

        // 渲染送货清单
        // 先得到用户想要购买的商品！
        List<CartInfo> cartInfoList = cartFeignClient.getCartCheckedList(userId);
        // 声明一个集合来存储订单明细
        ArrayList<OrderDetail> detailArrayList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setSkuId(cartInfo.getSkuId());
            orderDetail.setSkuName(cartInfo.getSkuName());
            orderDetail.setImgUrl(cartInfo.getImgUrl());
            orderDetail.setSkuNum(cartInfo.getSkuNum());
            orderDetail.setOrderPrice(cartInfo.getSkuPrice());

            // 添加到集合
            detailArrayList.add(orderDetail);
        }
        // 计算总金额
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderDetailList(detailArrayList);
        orderInfo.sumTotalAmount();

        Map<String, Object> result = new HashMap<>();
        result.put("userAddressList", userAddressList);
        result.put("detailArrayList", detailArrayList);
        // 保存总金额
        result.put("totalNum", detailArrayList.size());
        result.put("totalAmount", orderInfo.getTotalAmount());

        // 获取流水号
        String tradeNo = orderService.getTradeNo(userId);
        result.put("tradeNo", tradeNo);

        return Result.ok(result);
    }

    /**
     * 提交订单
     *
     * @param orderInfo
     * @param request
     * @return
     */
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfo orderInfo, HttpServletRequest request) {
        // 获取到用户Id
        String userId = AuthContextHolder.getUserId(request);
        orderInfo.setUserId(Long.parseLong(userId));

        // 获取前台页面的流水号
        String tradeNo = request.getParameter("tradeNo");

        // 调用服务层的比较方法
        boolean flag = orderService.checkTradeCode(userId, tradeNo);
        if (!flag) {
            // 比较失败！
            return Result.fail().message("不能重复提交订单！");
        }
        //  删除流水号
        orderService.deleteTradeNo(userId);

//        // 验证库存：
//        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
//        for (OrderDetail orderDetail : orderDetailList) {
//            // 验证库存：
//            boolean result = orderService.checkStock(orderDetail.getSkuId(), orderDetail.getSkuNum());
//            if (!result) {
//                return Result.fail().message(orderDetail.getSkuName() + "库存不足！");
//            }
//            // 验证价格：
//            BigDecimal skuPrice = productFeignClient.getSkuPrice(orderDetail.getSkuId());
//            if (orderDetail.getOrderPrice().compareTo(skuPrice) != 0) {
//                // 重新查询价格！
//                //  设置最新的价格
//                List<CartInfo> cartCheckedList = this.cartFeignClient.getCartCheckedList(userId);
////  写入缓存：
//                cartCheckedList.forEach(cartInfo -> {
//                    this.redisTemplate.opsForHash().put(RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX, cartInfo.getSkuId().toString(), cartInfo);
//                });
//                return Result.fail().message(orderDetail.getSkuName() + "价格有变动！");
//            }
//        }
        List<String> errorList = new ArrayList<>();
        List<CompletableFuture> futureList = new ArrayList<>();
        // 验证库存：
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            CompletableFuture<Void> checkStockCompletableFuture = CompletableFuture.runAsync(() -> {
                // 验证库存：
                boolean result = orderService.checkStock(orderDetail.getSkuId(), orderDetail.getSkuNum());
                if (!result) {
                    errorList.add(orderDetail.getSkuName() + "库存不足！");
                }
            }, threadPoolExecutor);
            futureList.add(checkStockCompletableFuture);

            CompletableFuture<Void> checkPriceCompletableFuture = CompletableFuture.runAsync(() -> {
                // 验证价格：
                BigDecimal skuPrice = productFeignClient.getSkuPrice(orderDetail.getSkuId());
                if (orderDetail.getOrderPrice().compareTo(skuPrice) != 0) {
                    // 重新查询价格！
                    List<CartInfo> cartCheckedList = this.cartFeignClient.getCartCheckedList(userId);
                    //  写入缓存：
                    cartCheckedList.forEach(cartInfo -> {
                        this.redisTemplate.opsForHash().put(RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX, cartInfo.getSkuId().toString(), cartInfo);
                    });
                    errorList.add(orderDetail.getSkuName() + "价格有变动！");
                }
            }, threadPoolExecutor);
            futureList.add(checkPriceCompletableFuture);
        }

        //合并线程
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()])).join();
        if (errorList.size() > 0) {
            return Result.fail().message(StringUtils.join(errorList, ","));
        }

        // 验证通过，保存订单！
        Long orderId = orderService.saveOrderInfo(orderInfo);
        return Result.ok(orderId);
    }

    @ApiOperation("我的订单")
    @GetMapping("auth/{page}/{limit}")
    public Result<IPage<OrderInfo>> index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            HttpServletRequest request) {
        // 获取到用户Id
        String userId = AuthContextHolder.getUserId(request);

        Page<OrderInfo> pageParam = new Page<>(page, limit);
        IPage<OrderInfo> pageModel = orderService.getPage(pageParam, userId);
        return Result.ok(pageModel);
    }

    /**
     * 内部调用获取订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("inner/getOrderInfo/{orderId}")
    public OrderInfo getOrderInfo(@PathVariable(value = "orderId") Long orderId) {
        return orderService.getOrderInfo(orderId);
    }

    /**
     * 秒杀提交订单，秒杀订单不需要做前置判断，直接下单
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("inner/seckill/submitOrder")
    public Long submitOrder(@RequestBody OrderInfo orderInfo) {
        Long orderId = orderService.saveOrderInfo(orderInfo);
        return orderId;
    }
}
