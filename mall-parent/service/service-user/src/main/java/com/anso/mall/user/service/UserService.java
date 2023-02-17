package com.anso.mall.user.service;

import com.anso.mall.model.user.UserInfo;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-08 11:43
 */
public interface UserService {
    /**
     * 登录方法
     *
     * @param userInfo
     * @return
     */
    UserInfo login(UserInfo userInfo);
}
