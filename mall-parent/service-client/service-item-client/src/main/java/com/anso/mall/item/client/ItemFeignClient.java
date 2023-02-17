package com.anso.mall.item.client;

import com.anso.mall.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-30 10:11
 */
@FeignClient(value = "service-item", fallback = ItemDegradeFeignClient.class)
public interface ItemFeignClient {
    /**
     * @param skuId
     * @return
     */
    @GetMapping("/api/item/{skuId}")
    Result getItem(@PathVariable("skuId") Long skuId);
}
