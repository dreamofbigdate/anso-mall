package com.anso.mall.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 16:07
 */
@SpringBootApplication
@ComponentScan({"com.anso.mall"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.anso.mall"})
public class ServicePaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePaymentApplication.class, args);
    }
}
