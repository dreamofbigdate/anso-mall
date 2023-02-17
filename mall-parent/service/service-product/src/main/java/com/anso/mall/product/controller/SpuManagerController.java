package com.anso.mall.product.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.model.product.BaseSaleAttr;
import com.anso.mall.model.product.SpuInfo;
import com.anso.mall.product.service.ManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-12 15:21
 */
@Api(tags = "spu管理接口")
@RestController
@RequestMapping("admin/product")
public class SpuManagerController {
    @Autowired
    private ManageService manageService;

    // 根据查询条件封装控制器
    // springMVC 的时候，有个叫对象属性传值 如果页面提交过来的参数与实体类的参数一致，
    // 则可以使用实体类来接收数据
    // http://api.gmall.com/admin/product/1/10?category3Id=61
    @GetMapping("{page}/{size}")
    public Result getSpuInfoPage(@PathVariable Long page,
                                 @PathVariable Long size,
                                 SpuInfo spuInfo) {

        // 创建一个Page 对象
        Page<SpuInfo> spuInfoPage = new Page<>(page, size);
        IPage<SpuInfo> spuInfoPageList = manageService.getSpuInfoPage(spuInfoPage, spuInfo);
        return Result.ok(spuInfoPageList);
    }

    // 销售属性http://api.gmall.com/admin/product/baseSaleAttrList
    @GetMapping("baseSaleAttrList")
    public Result baseSaleAttrList() {
        // 查询所有的销售属性集合
        List<BaseSaleAttr> baseSaleAttrList = manageService.getBaseSaleAttrList();

        return Result.ok(baseSaleAttrList);
    }

    /**
     * 保存spu
     *
     * @param spuInfo
     * @return
     */
    @PostMapping("updateSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfo spuInfo) {
        // 调用服务层的保存方法
        manageService.saveSpuInfo(spuInfo);
        return Result.ok();
    }
}
