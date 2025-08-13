package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags="菜品接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation(value = "新增菜品及对应口味")
    public Result addDishWithFlavor(@RequestBody DishDTO dishDTO){
        dishService.addDishWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")
    public Result delete(@RequestParam List<Long> ids){
        dishService.delete(ids);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品数据")
    public Result<DishVO> getById(@PathVariable Long id){
        DishVO dishVO=dishService.getById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    @ApiOperation(value = "修改菜品及对应口味")
    public Result update(@RequestBody DishDTO dishDTO){
        dishService.updateDishWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "根据分类id查询菜品")
    public Result<List<Dish>> queryByCategoryId(@RequestParam Long categoryId){
        List<Dish> dishes=dishService.queryByCategoryId(categoryId);
        log.info("分类id：{}, 菜品：{}",categoryId,dishes);

        return Result.success(dishes);
    }

}
