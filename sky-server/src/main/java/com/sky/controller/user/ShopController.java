package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
@Api(tags="用户店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String KEY="SHOP_STATUS";


    @GetMapping("/status")
    @ApiOperation(value = "获取状态")
    public Result<Integer> getStatus(){
        log.info("getStatus--");
        Integer status=(Integer) redisTemplate.opsForValue().get(KEY);
        return Result.success(status);
    }
}
