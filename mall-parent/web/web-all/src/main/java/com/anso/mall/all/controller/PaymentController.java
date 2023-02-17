package com.anso.mall.all.controller;

import com.anso.mall.model.order.OrderInfo;
import com.anso.mall.order.client.OrderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 16:13
 */
@Controller
public class PaymentController {
    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 支付页
     *
     * @param request
     * @return
     */
    @GetMapping("pay.html")
    public String success(HttpServletRequest request, Model model) {
        String orderId = request.getParameter("orderId");
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(Long.parseLong(orderId));
        model.addAttribute("orderInfo", orderInfo);
        return "payment/pay";
    }

    /**
     * 支付成功页
     *
     * @return
     */
    @GetMapping("pay/success.html")
    public String success() {
        return "payment/success";
    }
}
