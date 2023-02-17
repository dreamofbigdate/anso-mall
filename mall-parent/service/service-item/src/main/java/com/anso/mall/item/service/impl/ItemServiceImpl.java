package com.anso.mall.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.anso.mall.item.service.ItemService;
import com.anso.mall.list.client.ListFeignClient;
import com.anso.mall.model.product.*;
import com.anso.mall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-30 10:33
 */
@Service
public class ItemServiceImpl implements ItemService {
    //  远程调用service-product-client
    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ListFeignClient listFeignClient;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Map<String, Object> getBySkuId(Long skuId) {
        Map<String, Object> result = new HashMap<>();

        //异步编排
        // 通过skuId 查询skuInfo
        CompletableFuture<SkuInfo> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            // 保存skuInfo
            result.put("skuInfo", skuInfo);
            return skuInfo;
        }, threadPoolExecutor);


        // 销售属性-销售属性值回显并锁定
        CompletableFuture<Void> spuSaleAttrCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            List<SpuSaleAttr> spuSaleAttrList = productFeignClient.getSpuSaleAttrListCheckBySku(skuInfo.getId(), skuInfo.getSpuId());

            // 保存数据
            result.put("spuSaleAttrList", spuSaleAttrList);
        }, threadPoolExecutor);

        //根据spuId 查询map 集合属性
        // 销售属性-销售属性值回显并锁定
        CompletableFuture<Void> skuValueIdsMapCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());

            String valuesSkuJson = JSON.toJSONString(skuValueIdsMap);
            // 保存valuesSkuJson
            result.put("valuesSkuJson", valuesSkuJson);
        }, threadPoolExecutor);


        //获取商品最新价格
        CompletableFuture<Void> skuPriceCompletableFuture = CompletableFuture.runAsync(() -> {
            BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
            result.put("price", skuPrice);
        }, threadPoolExecutor);


        //获取分类信息
        CompletableFuture<Void> categoryViewCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());

            //分类信息
            result.put("categoryView", categoryView);
        }, threadPoolExecutor);

        //更新商品incrHotScore
        CompletableFuture<Void> incrHotScoreCompletableFuture = CompletableFuture.runAsync(() -> {
            listFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);

//  获取海报数据
        CompletableFuture<Void> spuPosterListCompletableFuture = skuCompletableFuture.thenAcceptAsync(skuInfo -> {
            //  spu海报数据
            List<SpuPoster> spuPosterList = productFeignClient.getSpuPosterBySpuId(skuInfo.getSpuId());
            result.put("spuPosterList", spuPosterList);
        }, threadPoolExecutor);

//  获取sku平台属性，即规格数据
        CompletableFuture<Void> skuAttrListCompletableFuture = CompletableFuture.runAsync(() -> {
            List<BaseAttrInfo> attrList = productFeignClient.getAttrList(skuId);
            //  使用拉姆达表示
            List<Map<String, String>> skuAttrList = attrList.stream().map((baseAttrInfo) -> {
                Map<String, String> attrMap = new HashMap<>();
                attrMap.put("attrName", baseAttrInfo.getAttrName());
                attrMap.put("attrValue", baseAttrInfo.getAttrValueList().get(0).getValueName());
                return attrMap;
            }).collect(Collectors.toList());
            result.put("skuAttrList", skuAttrList);
        }, threadPoolExecutor);


        CompletableFuture.allOf(skuCompletableFuture, spuSaleAttrCompletableFuture, skuValueIdsMapCompletableFuture, skuPriceCompletableFuture, categoryViewCompletableFuture, spuPosterListCompletableFuture, skuAttrListCompletableFuture, incrHotScoreCompletableFuture).join();
        return result;
    }
}
//  获取到的数据是skuInfo + skuImageList
//        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
//
//        //  判断skuInfo 不为空
//        if (skuInfo != null) {
//            //  获取分类数据
//            BaseCategoryView categoryView = productFeignClient.getCategoryView(skuInfo.getCategory3Id());
//            result.put("categoryView", categoryView);
//            //  获取销售属性+销售属性值
//            List<SpuSaleAttr> spuSaleAttrListCheckBySku = productFeignClient.getSpuSaleAttrListCheckBySku(skuId, skuInfo.getSpuId());
//            result.put("spuSaleAttrList", spuSaleAttrListCheckBySku);
//            //  查询销售属性值Id 与skuId 组合的map
//            Map skuValueIdsMap = productFeignClient.getSkuValueIdsMap(skuInfo.getSpuId());
//            //  将这个map 转换为页面需要的Json 对象
//            String valueJson = JSON.toJSONString(skuValueIdsMap);
//            result.put("valuesSkuJson", valueJson);
//
//        }
//        //  获取价格
//        BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
//        //  map 中 key 对应的谁? Thymeleaf 获取数据的时候 ${skuInfo.skuName}
//        result.put("skuInfo", skuInfo);
//        result.put("price", skuPrice);
//        //  返回map 集合 Thymeleaf 渲染：能用map 存储数据！
//        //  spu海报数据
//        List<SpuPoster> spuPosterList = productFeignClient.getSpuPosterBySpuId(skuInfo.getSpuId());
//        result.put("spuPosterList", spuPosterList);
//        List<BaseAttrInfo> attrList = productFeignClient.getAttrList(skuId);
//        //  使用拉姆达表示
//        List<Map<String, String>> skuAttrList = attrList.stream().map((baseAttrInfo) -> {
//            Map<String, String> attrMap = new HashMap<>();
//            attrMap.put("attrName", baseAttrInfo.getAttrName());
//            attrMap.put("attrValue", baseAttrInfo.getAttrValueList().get(0).getValueName());
//            return attrMap;
//        }).collect(Collectors.toList());
//        result.put("skuAttrList", skuAttrList);
//        return result;




