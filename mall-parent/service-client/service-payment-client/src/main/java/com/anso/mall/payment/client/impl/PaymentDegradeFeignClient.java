package com.anso.mall.payment.client.impl;

import com.anso.mall.model.payment.PaymentInfo;
import com.anso.mall.payment.client.PaymentFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 19:53
 */
@Component
public class PaymentDegradeFeignClient implements PaymentFeignClient {
    @Override
    public Boolean closePay(Long orderId) {
        return null;
    }

    @Override
    public Boolean checkPayment(Long orderId) {
        return null;
    }

    @Override
    public PaymentInfo getPaymentInfo(String outTradeNo) {
        return null;
    }
}
