package com.anso.mall.item.service;

import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-30 10:33
 */
public interface ItemService {
    /**
     * 获取sku详情信息
     *
     * @param skuId
     * @return
     */
    Map<String, Object> getBySkuId(Long skuId);
}
