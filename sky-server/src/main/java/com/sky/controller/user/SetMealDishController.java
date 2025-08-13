package com.sky.controller.user;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealDishService;
import com.sky.vo.SetmealDishVO;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userSetMealDishController")
@RequestMapping("/user/setmeal")
@Slf4j
@Api(tags="套餐相关接口")
public class SetMealDishController {

    @Autowired
    private SetMealDishService setMealDishService;


    @GetMapping("/list")
    public Result<List<Setmeal>> getByCategoryId(@RequestParam Long categoryId){
        List<Setmeal> setmeals=setMealDishService.getByCategoryId(categoryId);
        return Result.success(setmeals);
    }

    @GetMapping("/dish/{id}")
    public Result<List<SetmealDishVO>> getSetmaelDishesBySetmealId(@PathVariable Long id){
        List<SetmealDishVO> setmealDishVOS=setMealDishService.getSetmealDishesById(id);
        return  Result.success(setmealDishVOS);
    }



}
