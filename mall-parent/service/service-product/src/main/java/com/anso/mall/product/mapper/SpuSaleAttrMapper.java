package com.anso.mall.product.mapper;

import com.anso.mall.model.product.SpuSaleAttr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-12 16:51
 */
@Mapper
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {
    // 根据spuId 查询销售属性集合
    List<SpuSaleAttr> selectSpuSaleAttrList(Long spuId);

    // 根据spuId，skuId 查询销售属性集合
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(@Param("skuId") Long skuId, @Param("spuId") Long spuId);
}
