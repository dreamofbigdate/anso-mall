package com.anso.mall.cart;

import com.anso.mall.cart.client.CartDegradeFeignClient;
import com.anso.mall.common.result.Result;
import com.anso.mall.model.cart.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 17:35
 */
@FeignClient(value = "service-cart", fallback = CartDegradeFeignClient.class)
public interface CartFeignClient {
    @GetMapping("api/cart/addToCart/{skuId}/{skuNum}")
    Result addToCart(@PathVariable("skuId") Long skuId,
                     @PathVariable("skuNum") Integer skuNum,
                     HttpServletRequest request);

    @DeleteMapping("api/cart/deleteCart/{skuId}")
    Result deleteCart(@PathVariable("skuId") Long skuId,
                      HttpServletRequest request);

    @GetMapping("api/cart/checkCart/{skuId}/{isChecked}")
    Result checkCart(@PathVariable Long skuId,
                     @PathVariable Integer isChecked,
                     HttpServletRequest request);

    @GetMapping("api/cart/cartList")
    Result cartList(HttpServletRequest request);

    //  获取选中购物车列表！
    @GetMapping("/api/cart/getCartCheckedList/{userId}")
    List<CartInfo> getCartCheckedList(@PathVariable String userId);
}
