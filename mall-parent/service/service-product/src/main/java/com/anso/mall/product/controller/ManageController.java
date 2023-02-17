package com.anso.mall.product.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.model.product.*;
import com.anso.mall.product.service.ManageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-04 10:02
 */
@Api(tags = "商品基础属性接口")
@RestController
@RequestMapping("/admin/product/")
@RefreshScope
public class ManageController {

    @Autowired
    private ManageService manageService;

    //http://localhost/admin/product/getCategory1
    @GetMapping("getCategory1")
    public Result getCategory1() {
        List<BaseCategory1> category1List = manageService.getCategory1();
        return Result.ok(category1List);
    }

    //http://localhost/admin/product/getCategory2/{category1Id}
    @GetMapping("getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable long category1Id) {
        List<BaseCategory2> category2List = manageService.getCategory2(category1Id);
        return Result.ok(category2List);
    }

    //http://localhost/admin/product/getCategory3/{category2Id}
    @GetMapping("getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable long category2Id) {
        List<BaseCategory3> category3List = manageService.getCategory3(category2Id);
        return Result.ok(category3List);
    }

    //http://localhost/admin/product/attrInfoList/{category1Id}/{category2Id}/{category3Id}
    @GetMapping("attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result getAttrInfoList(@PathVariable long category1Id,
                                  @PathVariable long category2Id,
                                  @PathVariable long category3Id) {
        List<BaseAttrInfo> BaseAttrInfoList = manageService.getAttrInfoList(category1Id, category2Id, category3Id);
        return Result.ok(BaseAttrInfoList);
    }

    /**
     * 保存平台属性方法
     *
     * @param baseAttrInfo
     * @return
     */
    @PostMapping("saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo) {
        // 前台数据都被封装到该对象中baseAttrInfo
        manageService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }

    @GetMapping("getAttrValueList/{attrId}")
    public Result<List<BaseAttrValue>> getAttrValueList(@PathVariable("attrId") Long attrId) {
        BaseAttrInfo baseAttrInfo = manageService.getAttrInfo(attrId);
        List<BaseAttrValue> baseAttrValueList = baseAttrInfo.getAttrValueList();
        return Result.ok(baseAttrValueList);
    }

}
