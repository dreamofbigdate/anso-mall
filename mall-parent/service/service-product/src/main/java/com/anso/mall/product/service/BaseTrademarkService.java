package com.anso.mall.product.service;

import com.anso.mall.model.product.BaseTrademark;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-12 15:43
 */
public interface BaseTrademarkService extends IService<BaseTrademark> {

    /**
     * Banner分页列表
     *
     * @param pageParam
     * @return
     */
    IPage<BaseTrademark> getPage(Page<BaseTrademark> pageParam);

}
