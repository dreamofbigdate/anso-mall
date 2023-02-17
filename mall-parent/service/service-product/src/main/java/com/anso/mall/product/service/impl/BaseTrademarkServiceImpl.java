package com.anso.mall.product.service.impl;

import com.anso.mall.model.product.BaseTrademark;
import com.anso.mall.product.mapper.BaseTrademarkMapper;
import com.anso.mall.product.service.BaseTrademarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-12 15:46
 */
@Service
public class BaseTrademarkServiceImpl extends ServiceImpl<BaseTrademarkMapper, BaseTrademark> implements BaseTrademarkService {

    @Autowired
    private BaseTrademarkMapper baseTrademarkMapper;

    @Override
    public IPage<BaseTrademark> getPage(Page<BaseTrademark> pageParam) {
        QueryWrapper<BaseTrademark> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");

        IPage<BaseTrademark> page = baseTrademarkMapper.selectPage(pageParam, queryWrapper);
        return page;
    }
}