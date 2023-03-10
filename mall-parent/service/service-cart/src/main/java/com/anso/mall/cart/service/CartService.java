package com.anso.mall.cart.service;

import com.anso.mall.model.cart.CartInfo;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 14:39
 */
public interface CartService {
    // 添加购物车 用户Id，商品Id，商品数量。
    void addToCart(Long skuId, String userId, Integer skuNum);

    /**
     * 通过用户Id 查询购物车列表
     *
     * @param userId
     * @param userTempId
     * @return
     */
    List<CartInfo> getCartList(String userId, String userTempId);

    /**
     * 更新选中状态
     *
     * @param userId
     * @param isChecked
     * @param skuId
     */
    void checkCart(String userId, Integer isChecked, Long skuId);

    //删除购物车
    void deleteCart(Long skuId, String userId);

    /**
     * 根据用户Id 查询购物车列表
     *
     * @param userId
     * @return
     */
    List<CartInfo> getCartCheckedList(String userId);
}
