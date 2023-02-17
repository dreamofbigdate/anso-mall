package com.anso.mall.order.mapper;

import com.anso.mall.model.order.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-14 9:31
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    IPage<OrderInfo> selectPageByUserId(Page<OrderInfo> page, @Param("userId") String userId);
}
