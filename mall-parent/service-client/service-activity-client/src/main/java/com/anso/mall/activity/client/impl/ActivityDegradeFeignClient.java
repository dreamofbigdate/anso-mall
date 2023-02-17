package com.anso.mall.activity.client.impl;

import com.anso.mall.activity.client.ActivityFeignClient;
import com.anso.mall.common.result.Result;

import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 20:56
 */
public class ActivityDegradeFeignClient implements ActivityFeignClient {
    @Override
    public Result findAll() {
        return Result.fail();
    }

    @Override
    public Result getSeckillGoods(Long skuId) {
        return Result.fail();
    }

    @Override
    public Result<Map<String, Object>> trade() {
        return Result.fail();
    }
}
