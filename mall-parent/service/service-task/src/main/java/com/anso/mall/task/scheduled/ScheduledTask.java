package com.anso.mall.task.scheduled;

import com.anso.mall.common.constant.MqConst;
import com.anso.mall.common.service.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 20:32
 */
@Component
@EnableScheduling
@Slf4j
public class ScheduledTask {
    @Autowired
    private RabbitService rabbitService;

    /**
     * 每天凌晨1点执行
     */
    //@Scheduled(cron = "0/30 * * * * ?")
    @Scheduled(cron = "0 0 1 * * ?")
    public void task1() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_1, "");
    }

    /**
     * 每天下午18点执行
     */
    //@Scheduled(cron = "0/35 * * * * ?")
    @Scheduled(cron = "0 0 18 * * ?")
    public void task18() {

        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK, MqConst.ROUTING_TASK_18, "");
    }
}
