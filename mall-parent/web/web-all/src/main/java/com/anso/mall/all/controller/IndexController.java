package com.anso.mall.all.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sc
 * @Description //TODO  缓存渲染方式，读取缓存
 * @create 2023-02-02 15:42
 */
@Controller
public class IndexController {
    @Autowired
    private ProductFeignClient productFeignClient;

    @GetMapping({"/", "index.html"})
    public String index(HttpServletRequest request) {
        // 获取首页分类数据
        Result result = productFeignClient.getBaseCategoryList();
        request.setAttribute("list", result.getData());
        return "index/index";
    }
}
