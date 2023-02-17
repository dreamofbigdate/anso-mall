package com.anso.mall.all.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.order.client.OrderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-14 9:21
 */
@Controller
public class OrderController {
    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 确认订单
     *
     * @param model
     * @return
     */
    @GetMapping("trade.html")
    public String trade(Model model) {
        Result<Map<String, Object>> result = orderFeignClient.trade();

        model.addAllAttributes(result.getData());
        return "order/trade";
    }

    /**
     * 我的订单
     *
     * @return
     */
    @GetMapping("myOrder.html")
    public String myOrder() {
        return "order/myOrder";
    }
}
