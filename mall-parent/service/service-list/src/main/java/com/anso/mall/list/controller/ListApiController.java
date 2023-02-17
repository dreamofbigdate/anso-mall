package com.anso.mall.list.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.list.service.SearchService;
import com.anso.mall.model.list.Goods;
import com.anso.mall.model.list.SearchParam;
import com.anso.mall.model.list.SearchResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-03 17:13
 */
@RestController
@RequestMapping("api/list")
public class ListApiController {
    @Autowired
    private SearchService searchService;

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    /**
     * @return
     */
    @GetMapping("inner/createIndex")
    public Result createIndex() {
//        restTemplate.createIndex(Goods.class);
//        restTemplate.putMapping(Goods.class);
        IndexOperations indexOperations = restTemplate.indexOps(Goods.class);
        indexOperations.create();
        Document mapping = indexOperations.createMapping(Goods.class);
        indexOperations.putMapping(mapping);
        return Result.ok();
    }

    /**
     * 上架商品
     *
     * @param skuId
     * @return
     */
    @GetMapping("inner/upperGoods/{skuId}")
    public Result upperGoods(@PathVariable("skuId") Long skuId) {
        searchService.upperGoods(skuId);
        return Result.ok();
    }

    /**
     * 下架商品
     *
     * @param skuId
     * @return
     */
    @GetMapping("inner/lowerGoods/{skuId}")
    public Result lowerGoods(@PathVariable("skuId") Long skuId) {
        searchService.lowerGoods(skuId);
        return Result.ok();
    }

    /**
     * 更新商品incrHotScore
     *
     * @param skuId
     * @return
     */
    @GetMapping("inner/incrHotScore/{skuId}")
    public Result incrHotScore(@PathVariable("skuId") Long skuId) {
        // 调用服务层
        searchService.incrHotScore(skuId);
        return Result.ok();
    }

    /**
     * 搜索商品
     *
     * @param searchParam
     * @return
     * @throws IOException
     */
    @PostMapping
    public Result list(@RequestBody SearchParam searchParam) throws IOException {
        SearchResponseVo response = searchService.search(searchParam);
        return Result.ok(response);
    }
}
