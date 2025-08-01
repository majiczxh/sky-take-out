package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 添加菜品及口味
     * @param dishDTO
     */
    void addDishWithFlavor(DishDTO dishDTO);
}
