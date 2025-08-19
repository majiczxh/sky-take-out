package com.sky.controller.user;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags="菜品接口")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation(value = "根据分类id查询菜品")
    public Result<List<DishVO>> queryByCategoryId(@RequestParam Long categoryId){

        //构造key
        String key="dish_"+categoryId;

        //在redis中查询，如果有数据直接返回
        List<DishVO> list=(List<DishVO>)redisTemplate.opsForValue().get(key);
        if(list!=null&&list.size()>0){
            return Result.success(list);
        }

        //查询数据库
        list=dishService.queryWithFlavorsByCategoryId(categoryId);

        //将数据放入redis中
        redisTemplate.opsForValue().set(key,list);

        log.info("分类id：{}, 菜品：{}",categoryId,list);
        return Result.success(list);
    }

}
