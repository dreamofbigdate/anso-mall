package com.anso.mall.payment.service;

import com.alipay.api.AlipayApiException;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 16:21
 */
public interface AlipayService {
    String createaliPay(Long orderId);

    /***
     * 关闭交易
     * @param orderId
     * @return
     */
    Boolean closePay(Long orderId) throws AlipayApiException;

    /**
     * 根据订单查询是否支付成功！
     *
     * @param orderId
     * @return
     */
    Boolean checkPayment(Long orderId);
}
