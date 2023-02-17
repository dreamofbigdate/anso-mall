package com.anso.mall.order.client.impl;

import com.anso.mall.common.result.Result;
import com.anso.mall.model.order.OrderInfo;
import com.anso.mall.order.client.OrderFeignClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-13 19:56
 */
@Component
public class OrderDegradeFeignClient implements OrderFeignClient {
    @Override
    public Result<Map<String, Object>> trade() {
        return null;
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return null;
    }

    @Override
    public Long submitOrder(OrderInfo orderInfo) {
        return null;
    }
}
