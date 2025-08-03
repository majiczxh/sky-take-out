package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SetMealDishService {
    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * saveWithDishes
     * @param setmealDTO
     */
    void saveWithDishes(SetmealDTO setmealDTO);

    /**
     * startOrStop setmeal
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * getSetmealWithDishesById
     * @param id
     * @return
     */
    SetmealVO getSetmealWithDishesById(Long id);

    /**
     * delete Setmeals With SetmealDishes By Ids
     * @param ids
     */
    void deleteSetmealsWithSetmealDishesByIds(List<Long> ids);
}
