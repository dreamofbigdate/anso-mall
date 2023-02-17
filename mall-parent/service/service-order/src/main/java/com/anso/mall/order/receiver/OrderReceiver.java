package com.anso.mall.order.receiver;

import com.anso.mall.common.constant.MqConst;
import com.anso.mall.model.order.OrderInfo;
import com.anso.mall.model.payment.PaymentInfo;
import com.anso.mall.order.service.OrderService;
import com.anso.mall.payment.client.PaymentFeignClient;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 15:47
 */
@Component
public class OrderReceiver {
    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    //  监听的消息
    @SneakyThrows
    @RabbitListener(queues = MqConst.QUEUE_ORDER_CANCEL)
    public void cancelOrder(Long orderId, Message message, Channel channel) {
        //  判断当前订单Id 不能为空
        try {
            if (orderId != null) {
                //  发过来的是订单Id，那么你就需要判断一下当前的订单是否已经支付了。
                //  未支付的情况下：关闭订单
                //  根据订单Id 查询orderInfo select * from order_info where id = orderId
                //  利用这个接口IService  实现类ServiceImpl 完成根据订单Id 查询订单信息 ServiceImpl 类底层还是使用的mapper
                OrderInfo orderInfo = orderService.getById(orderId);
                //  判断支付状态,进度状态
                if (orderInfo != null && "UNPAID".equals(orderInfo.getOrderStatus())
                        && "UNPAID".equals(orderInfo.getProcessStatus())) {
                    //  关闭过期订单！ 还需要关闭对应的 paymentInfo ，还有alipay.
                    //  orderService.execExpiredOrder(orderId);
                    //  查询paymentInfo 是否存在！
                    PaymentInfo paymentInfo = paymentFeignClient.getPaymentInfo(orderInfo.getOutTradeNo());
                    //  判断 用户点击了扫码支付
                    if (paymentInfo != null && "UNPAID".equals(paymentInfo.getPaymentStatus())) {

                        //  查看是否有交易记录！
                        Boolean flag = paymentFeignClient.checkPayment(orderId);
                        //  判断
                        if (flag) {
                            //  flag = true , 有交易记录
                            //  调用关闭接口！ 扫码未支付这样才能关闭成功！
                            Boolean result = paymentFeignClient.closePay(orderId);
                            //  判断
                            if (result) {
                                //  result = true; 关闭成功！未付款！需要关闭orderInfo， paymentInfo，Alipay
                                orderService.execExpiredOrder(orderId, "2");
                            } else {
                                //  result = false; 表示付款！
                                //  说明已经付款了！ 正常付款成功都会走异步通知！
                            }
                        } else {
                            //  没有交易记录，不需要关闭支付！  需要关闭orderInfo， paymentInfo
                            orderService.execExpiredOrder(orderId, "2");
                        }

                    } else {
                        //  只关闭订单orderInfo！
                        orderService.execExpiredOrder(orderId, "1");
                    }
                }
            }
        } catch (Exception e) {
            //  消息没有正常被消费者处理： 记录日志后续跟踪处理!

            e.printStackTrace();
        }
        //  手动确认消息 如果不确认，有可能会到消息残留。
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    // 订单支付成功，更义订单状态
//    @SneakyThrows
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = MqConst.QUEUE_PAYMENT_PAY,durable = "true"),
//            exchange = @Exchange(value = MqConst.EXCHANGE_DIRECT_PAYMENT_PAY),
//            key = {MqConst.ROUTING_PAYMENT_PAY}))
//    public void upOrder(long orderId,Message message,Channel channel){
//
//    }
}
