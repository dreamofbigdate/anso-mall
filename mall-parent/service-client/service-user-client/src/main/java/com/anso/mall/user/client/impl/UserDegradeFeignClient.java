package com.anso.mall.user.client.impl;

import com.anso.mall.model.user.UserAddress;
import com.anso.mall.user.client.UserFeignClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 19:37
 */
@Component
public class UserDegradeFeignClient implements UserFeignClient {
    @Override
    public List<UserAddress> findUserAddressListByUserId(String userId) {
        return null;
    }
}
