package com.anso.mall.product.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.anso.mall.product.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-01 14:30
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void testLock() {
// 查询redis中的num值
        String value = (String) this.stringRedisTemplate.opsForValue().get("num");
        // 没有该值return
        if (StringUtils.isBlank(value)) {
            return;
        }
        // 有值就转成成int
        int num = Integer.parseInt(value);
        // 把redis中的num值+1
        this.stringRedisTemplate.opsForValue().set("num", String.valueOf(++num));
    }
}
