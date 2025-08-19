package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealDishService;
import com.sky.vo.SetMealPageVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetMealDishController")
@RequestMapping("/admin/setmeal")
@Slf4j
@Api(tags="套餐相关接口")
public class SetMealDishController {

    @Autowired
    private SetMealDishService setMealDishService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult=setMealDishService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 添加菜品
     * 1.上传图片//
     * 2.输入相关数据
     * 3.选择菜品
     *      按分类查询菜品//
     */
    @PostMapping
    @ApiOperation(value = "新增套餐")
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.getCategoryId()")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setMealDishService.saveWithDishes(setmealDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation(value = "启售/停售套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        setMealDishService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id){
        SetmealVO setmealVO=setMealDishService.getSetmealWithDishesById(id);
        return Result.success(setmealVO);

    }

    @PutMapping
    @ApiOperation(value = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setMealDishService.update(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation(value = "删除套餐及套餐菜品")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result deleteByIds(@RequestParam List<Long> ids){
        setMealDishService.deleteSetmealsWithSetmealDishesByIds(ids);
        return Result.success();

    }


}
