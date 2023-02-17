package com.anso.mall.all.controller;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 17:03
 */

import com.anso.mall.cart.CartFeignClient;
import com.anso.mall.model.product.SkuInfo;
import com.anso.mall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 购物车页面
 * </p>
 */
@Controller
public class CartController {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 查看购物车
     *
     * @param
     * @return
     */
    @RequestMapping("cart.html")
    public String index() {
        return "cart/index";
    }

    /**
     * 添加购物车
     *
     * @param skuId
     * @param skuNum
     * @param request
     * @return
     */
    @RequestMapping("addCart.html")
    public String addCart(@RequestParam(name = "skuId") Long skuId,
                          @RequestParam(name = "skuNum") Integer skuNum,
                          HttpServletRequest request) {
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        request.setAttribute("skuInfo", skuInfo);
        request.setAttribute("skuNum", skuNum);
        return "cart/addCart";
    }
}

