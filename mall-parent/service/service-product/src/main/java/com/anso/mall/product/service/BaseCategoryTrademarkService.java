package com.anso.mall.product.service;

import com.anso.mall.model.product.BaseCategoryTrademark;
import com.anso.mall.model.product.BaseTrademark;
import com.anso.mall.model.product.CategoryTrademarkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-12 16:02
 */
public interface BaseCategoryTrademarkService extends IService<BaseCategoryTrademark> {

    /**
     * 根据三级分类获取品牌
     *
     * @param category3Id
     * @return
     */
    List<BaseTrademark> findTrademarkList(Long category3Id);

    /**
     * 保存分类与品牌关联
     *
     * @param categoryTrademarkVo
     */
    void save(CategoryTrademarkVo categoryTrademarkVo);

    /**
     * 获取当前未被三级分类关联的所有品牌
     *
     * @param category3Id
     * @return
     */
    List<BaseTrademark> findCurrentTrademarkList(Long category3Id);

    /**
     * 删除关联
     *
     * @param category3Id
     * @param trademarkId
     */
    void remove(Long category3Id, Long trademarkId);
}
