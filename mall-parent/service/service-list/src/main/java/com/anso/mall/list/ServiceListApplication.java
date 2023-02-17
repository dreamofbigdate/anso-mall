package com.anso.mall.list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-03 17:23
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.anso.mall")
@EnableDiscoveryClient
@EnableFeignClients("com.anso.mall")
public class ServiceListApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceListApplication.class, args);
    }
}
