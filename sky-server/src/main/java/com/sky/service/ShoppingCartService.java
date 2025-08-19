package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Category;
import com.sky.entity.ShoppingCart;
import com.sky.result.PageResult;

import java.util.List;

public interface ShoppingCartService {


    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void add(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车数据
     * @return
     */
    List<ShoppingCart> list();

    /**
     * 清空购物车数据
     */
    void clean();

    /**
     * 删除购物车数据
     * @param shoppingCartDTO
     */
    void sub(ShoppingCartDTO shoppingCartDTO);
}
