package com.anso.mall.product.mapper;

import com.anso.mall.model.product.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-29 10:42
 */
@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {
    // 根据spuId 查询map 集合数据
    List<Map> selectSaleAttrValuesBySpu(Long spuId);
}
