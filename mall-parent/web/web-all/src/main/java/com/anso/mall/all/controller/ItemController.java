package com.anso.mall.all.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.item.client.ItemFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-30 10:08
 */
@Controller
public class ItemController {
    @Autowired
    private ItemFeignClient itemFeignClient;

    /**
     * sku详情页面
     *
     * @param skuId
     * @param model
     * @return
     */
    @RequestMapping("{skuId}.html")
    public String getItem(@PathVariable Long skuId, Model model) {
        // 通过skuId 查询skuInfo
        Result<Map> result = itemFeignClient.getItem(skuId);
        model.addAllAttributes(result.getData());
        return "item/item";
    }
}
