package com.anso.mall.list.client.impl;

import com.anso.mall.common.result.Result;
import com.anso.mall.list.client.ListFeignClient;
import com.anso.mall.model.list.SearchParam;
import org.springframework.stereotype.Component;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-06 9:44
 */
@Component
public class ListDegradeFeignClient implements ListFeignClient {
    @Override
    public Result incrHotScore(Long skuId) {
        return null;
    }

    @Override
    public Result list(SearchParam listParam) {
        return null;
    }

    @Override
    public Result upperGoods(Long skuId) {
        return null;
    }

    @Override
    public Result lowerGoods(Long skuId) {
        return null;
    }
}
