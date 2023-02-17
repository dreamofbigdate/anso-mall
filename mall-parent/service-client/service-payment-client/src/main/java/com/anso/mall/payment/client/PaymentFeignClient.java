package com.anso.mall.payment.client;

import com.anso.mall.model.payment.PaymentInfo;
import com.anso.mall.payment.client.impl.PaymentDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 19:52
 */
@FeignClient(value = "service-payment", fallback = PaymentDegradeFeignClient.class)
public interface PaymentFeignClient {

    @GetMapping("api/payment/alipay/closePay/{orderId}")
    Boolean closePay(@PathVariable Long orderId);

    @GetMapping("api/payment/alipay/checkPayment/{orderId}")
    Boolean checkPayment(@PathVariable Long orderId);

    @GetMapping("api/payment/alipay/getPaymentInfo/{outTradeNo}")
    PaymentInfo getPaymentInfo(@PathVariable String outTradeNo);

}