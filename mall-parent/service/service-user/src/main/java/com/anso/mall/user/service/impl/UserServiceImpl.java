package com.anso.mall.user.service.impl;

import com.anso.mall.model.user.UserInfo;
import com.anso.mall.user.mapper.UserInfoMapper;
import com.anso.mall.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-08 11:44
 */
@Service
public class UserServiceImpl implements UserService {
    // 调用mapper 层
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo login(UserInfo userInfo) {
        // select * from userInfo where userName = ? and passwd = ?
        // 注意密码是加密：
        String passwd = userInfo.getPasswd(); //123
        // 将passwd 进行加密
        String newPasswd = DigestUtils.md5DigestAsHex(passwd.getBytes());

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name", userInfo.getLoginName());
        queryWrapper.eq("passwd", newPasswd);
        UserInfo info = userInfoMapper.selectOne(queryWrapper);
        if (info != null) {

            return info;
        }
        return null;
    }
}

