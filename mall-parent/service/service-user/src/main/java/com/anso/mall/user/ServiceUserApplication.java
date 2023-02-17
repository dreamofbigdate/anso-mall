package com.anso.mall.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-08 11:41
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.anso.mall")
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
