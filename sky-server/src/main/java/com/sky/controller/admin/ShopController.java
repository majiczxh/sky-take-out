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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags="管理员店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String KEY="SHOP_STATUS";


    @PutMapping("/{status}")
    @ApiOperation(value = "设置状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("setStatus--");
        redisTemplate.opsForValue().set(KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    @ApiOperation(value = "获取状态")
    public Result<Integer> getStatus(){
        log.info("getStatus--");
        Integer status=(Integer) redisTemplate.opsForValue().get(KEY);
        return Result.success(status);
    }
}
