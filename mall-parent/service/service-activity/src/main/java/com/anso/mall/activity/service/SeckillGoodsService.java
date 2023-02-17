package com.anso.mall.activity.service;

import com.anso.mall.common.result.Result;
import com.anso.mall.model.activity.SeckillGoods;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-16 20:53
 */
public interface SeckillGoodsService {
    /**
     * 返回全部列表
     *
     * @return
     */
    List<SeckillGoods> findAll();

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    SeckillGoods getSeckillGoods(Long id);

    /**
     * 根据用户和商品ID实现秒杀下单
     *
     * @param skuId
     * @param userId
     */
    void seckillOrder(Long skuId, String userId);

    /***
     * 根据商品id与用户ID查看订单信息
     * @param skuId
     * @param userId
     * @return
     */
    Result checkOrder(Long skuId, String userId);
}
