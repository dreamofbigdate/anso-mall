package com.anso.mall.item.client;

import com.anso.mall.common.result.Result;
import org.springframework.stereotype.Component;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-30 10:12
 */
@Component
public class ItemDegradeFeignClient implements ItemFeignClient {
    @Override
    public Result getItem(Long skuId) {
        return Result.fail();
    }
}
