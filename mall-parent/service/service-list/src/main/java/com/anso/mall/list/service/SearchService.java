package com.anso.mall.list.service;

import com.anso.mall.model.list.SearchParam;
import com.anso.mall.model.list.SearchResponseVo;

import java.io.IOException;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-06 9:25
 */
public interface SearchService {
    /**
     * 上架商品列表
     *
     * @param skuId
     */
    void upperGoods(Long skuId);

    /**
     * 下架商品列表
     *
     * @param skuId
     */
    void lowerGoods(Long skuId);

    /**
     * 更新热点
     *
     * @param skuId
     */
    void incrHotScore(Long skuId);

    /**
     * 搜索列表
     *
     * @param searchParam
     * @return
     * @throws IOException
     */
    SearchResponseVo search(SearchParam searchParam) throws IOException;
}
