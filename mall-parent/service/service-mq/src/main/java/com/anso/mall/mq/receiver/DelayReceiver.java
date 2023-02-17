package com.anso.mall.mq.receiver;

import com.anso.mall.mq.config.DelayedMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 15:37
 */
@Component
public class DelayReceiver {
    @RabbitListener(queues = DelayedMqConfig.queue_delay_1)
    public void get(String msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Receive queue_delay_1: " + sdf.format(new Date()) + " Delay rece." + msg);
    }
}
