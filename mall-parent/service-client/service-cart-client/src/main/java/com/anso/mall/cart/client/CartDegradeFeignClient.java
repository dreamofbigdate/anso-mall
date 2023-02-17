package com.anso.mall.cart.client;

import com.anso.mall.cart.CartFeignClient;
import com.anso.mall.common.result.Result;
import com.anso.mall.model.cart.CartInfo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 17:37
 */
@Component
public class CartDegradeFeignClient implements CartFeignClient {

    @Override
    public Result addToCart(Long skuId, Integer skuNum, HttpServletRequest request) {
        return null;
    }

    @Override
    public Result deleteCart(Long skuId, HttpServletRequest request) {
        return null;
    }

    @Override
    public Result checkCart(Long skuId, Integer isChecked, HttpServletRequest request) {
        return null;
    }

    @Override
    public Result cartList(HttpServletRequest request) {
        return null;
    }

    @Override
    public List<CartInfo> getCartCheckedList(String userId) {
        return null;
    }
}
