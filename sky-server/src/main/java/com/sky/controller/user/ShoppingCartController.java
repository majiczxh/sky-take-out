package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Category;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车相关接口")
@Slf4j
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;



    @PostMapping("/add")
    @ApiOperation(value = "添加购物车")
    public Result add(@RequestBody ShoppingCartDTO  shoppingCartDTO){
        log.info("购物车添加：{}",shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation(value = "查看购物车")
    public Result<List<ShoppingCart>> list(){
        List<ShoppingCart> list=shoppingCartService.list();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation(value = "清空购物车")
    public Result clean(){
       shoppingCartService.clean();
        return Result.success();
    }

    @PostMapping("/sub")
    @ApiOperation(value = "删除购物车")
    public Result sub(@RequestBody ShoppingCartDTO  shoppingCartDTO){
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }

}
