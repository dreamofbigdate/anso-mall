package com.anso.mall.cart.controller;

import com.anso.mall.cart.service.CartService;
import com.anso.mall.common.result.Result;
import com.anso.mall.common.util.AuthContextHolder;
import com.anso.mall.model.cart.CartInfo;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 14:50
 */
@Api(tags = "添加购物车接口")
@RestController
@RequestMapping("api/cart")
public class CartApiController {
    @Autowired
    HttpServletRequest request;
    @Autowired
    private CartService cartService;

    /**
     * 添加购物车
     *
     * @param skuId
     * @param skuNum
     * @param request
     * @return
     */
    @GetMapping("addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable("skuId") Long skuId,
                            @PathVariable("skuNum") Integer skuNum,
                            HttpServletRequest request) {
        // 如何获取userId
        String userId = AuthContextHolder.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            // 获取临时用户Id
            userId = AuthContextHolder.getUserTempId(request);
        }
        cartService.addToCart(skuId, userId, skuNum);
        return Result.ok();
    }

    /**
     * 查询购物车
     *
     * @param request
     * @return
     */
    @GetMapping("cartList")
    public Result cartList(HttpServletRequest request) {
        // 获取用户Id
        String userId = AuthContextHolder.getUserId(request);
        // 获取临时用户Id
        String userTempId = AuthContextHolder.getUserTempId(request);
        List<CartInfo> cartInfoList = cartService.getCartList(userId, userTempId);
        return Result.ok(cartInfoList);
    }

    //  选中状态
    @GetMapping("checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,
                            @PathVariable Integer isChecked,
                            HttpServletRequest request) {

        String userId = AuthContextHolder.getUserId(request);
        //  判断
        if (StringUtils.isEmpty(userId)) {
            userId = AuthContextHolder.getUserTempId(request);
        }
        //  调用服务层方法
        cartService.checkCart(userId, isChecked, skuId);
        return Result.ok();
    }


    /**
     * 删除
     *
     * @param skuId
     * @param request
     * @return
     */
    @DeleteMapping("deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId,
                             HttpServletRequest request) {
        // 如何获取userId
        String userId = AuthContextHolder.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            // 获取临时用户Id
            userId = AuthContextHolder.getUserTempId(request);
        }
        cartService.deleteCart(skuId, userId);
        return Result.ok();
    }

    /**
     * 根据用户Id 查询购物车列表
     *
     * @param userId
     * @return
     */
    @GetMapping("getCartCheckedList/{userId}")
    public List<CartInfo> getCartCheckedList(@PathVariable(value = "userId") String userId) {
        return cartService.getCartCheckedList(userId);
    }
}
