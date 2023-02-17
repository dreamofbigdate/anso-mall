package com.anso.mall.payment.service.impl;


import com.anso.mall.common.constant.MqConst;
import com.anso.mall.common.service.RabbitService;
import com.anso.mall.model.enums.PaymentStatus;
import com.anso.mall.model.order.OrderInfo;
import com.anso.mall.model.payment.PaymentInfo;
import com.anso.mall.payment.mapper.PaymentInfoMapper;
import com.anso.mall.payment.service.PaymentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 16:18
 */
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitService rabbitService;


    @Override
    public void savePaymentInfo(OrderInfo orderInfo, String paymentType) {
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderInfo.getId());
        queryWrapper.eq("payment_type", paymentType);
        Integer count = paymentInfoMapper.selectCount(queryWrapper);
        if (count > 0) return;

        // 保存交易记录
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(orderInfo.getId());
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID.name());
        paymentInfo.setSubject(orderInfo.getTradeBody());
        //paymentInfo.setSubject("test");
        paymentInfo.setTotalAmount(orderInfo.getTotalAmount());

        paymentInfoMapper.insert(paymentInfo);
    }

    @Override
    public PaymentInfo getPaymentInfo(String outTradeNo, String name) {
        //  select * from payment_info where out_trade_no = ? and payment_type = ?
        QueryWrapper<PaymentInfo> paymentInfoQueryWrapper = new QueryWrapper<>();
        paymentInfoQueryWrapper.eq("out_trade_no", outTradeNo);
        paymentInfoQueryWrapper.eq("payment_type", name);
        return paymentInfoMapper.selectOne(paymentInfoQueryWrapper);
    }

    @Override
    public void paySuccess(String outTradeNo, String paymentType, Map<String, String> paramMap) {
        //  根据outTradeNo，paymentType 查询
        PaymentInfo paymentInfoQuery = this.getPaymentInfo(outTradeNo, paymentType);
        if (paymentInfoQuery == null) {
            return;
        }

        try {
            //  改造一下更新的方法！
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setCallbackTime(new Date());
            paymentInfo.setPaymentStatus(PaymentStatus.PAID.name());
            paymentInfo.setCallbackContent(paramMap.toString());
            paymentInfo.setTradeNo(paramMap.get("trade_no"));
            //  查询条件也可以作为更新条件！
            this.updatePaymentInfo(outTradeNo, paymentType, paymentInfo);
        } catch (Exception e) {
            //  删除key
            this.redisTemplate.delete(paramMap.get("notify_id"));
            e.printStackTrace();
        }
        //  发送通知：更新订单的状态！
        this.rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_PAYMENT_PAY, MqConst.ROUTING_PAYMENT_PAY, paymentInfoQuery.getOrderId());

    }

    @Override
    public void updatePaymentInfo(String outTradeNo, String name, PaymentInfo paymentInfo) {
        QueryWrapper<PaymentInfo> paymentInfoQueryWrapper = new QueryWrapper<>();
        paymentInfoQueryWrapper.eq("out_trade_no", outTradeNo);
        paymentInfoQueryWrapper.eq("payment_type", name);
        paymentInfoMapper.update(paymentInfo, paymentInfoQueryWrapper);
    }

    @Override
    public void closePayment(Long orderId) {
        // 设置关闭交易记录的条件  118
        QueryWrapper<PaymentInfo> paymentInfoQueryWrapper = new QueryWrapper<>();
        paymentInfoQueryWrapper.eq("order_id", orderId);
        // 如果当前的交易记录不存在，则不更新交易记录
        Integer count = paymentInfoMapper.selectCount(paymentInfoQueryWrapper);
        if (null == count || count.intValue() == 0) return;
        // 在关闭支付宝交易之前。还需要关闭paymentInfo
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentStatus(PaymentStatus.CLOSED.name());
        paymentInfoMapper.update(paymentInfo, paymentInfoQueryWrapper);

    }


}

