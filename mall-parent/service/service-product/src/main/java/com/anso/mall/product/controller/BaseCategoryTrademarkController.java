package com.anso.mall.product.controller;

import com.anso.mall.common.result.Result;
import com.anso.mall.model.product.BaseTrademark;
import com.anso.mall.model.product.CategoryTrademarkVo;
import com.anso.mall.product.service.BaseCategoryTrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-01-12 16:01
 */
@RestController
@RequestMapping("admin/product/baseCategoryTrademark")
public class BaseCategoryTrademarkController {
    @Autowired
    private BaseCategoryTrademarkService baseCategoryTrademarkService;

    @PostMapping("save")
    public Result save(@RequestBody CategoryTrademarkVo categoryTrademarkVo) {
        //  保存方法
        baseCategoryTrademarkService.save(categoryTrademarkVo);
        return Result.ok();
    }

    @DeleteMapping("remove/{category3Id}/{trademarkId}")
    public Result remove(@PathVariable Long category3Id, @PathVariable Long trademarkId) {
        //  调用服务层方法
        baseCategoryTrademarkService.remove(category3Id, trademarkId);
        return Result.ok();
    }

    @GetMapping("findTrademarkList/{category3Id}")
    public Result findTrademarkList(@PathVariable Long category3Id) {
        //  select * from base_trademark
        List<BaseTrademark> list = baseCategoryTrademarkService.findTrademarkList(category3Id);
        //  返回
        return Result.ok(list);
    }

    @GetMapping("findCurrentTrademarkList/{category3Id}")
    public Result findCurrentTrademarkList(@PathVariable Long category3Id) {
        List<BaseTrademark> list = baseCategoryTrademarkService.findCurrentTrademarkList(category3Id);
        //  返回
        return Result.ok(list);
    }

}
