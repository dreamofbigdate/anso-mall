package com.anso.mall.item.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-30 10:34
 */
@RestController
@RequestMapping("api/item")
public class ItemApiController {
    @Autowired
    private ItemService itemService;

    /**
     * 获取sku详情信息
     *
     * @param skuId
     * @return
     */
    @GetMapping("{skuId}")
    public Result getItem(@PathVariable Long skuId) {
        Map<String, Object> result = itemService.getBySkuId(skuId);
        return Result.ok(result);
    }
}
