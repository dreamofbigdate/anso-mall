package com.anso.mall.user.service;

import com.anso.mall.model.user.UserAddress;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 19:32
 */
public interface UserAddressService {
    /**
     * 根据用户Id 查询用户的收货地址列表！
     *
     * @param userId
     * @return
     */
    List<UserAddress> findUserAddressListByUserId(String userId);
}
