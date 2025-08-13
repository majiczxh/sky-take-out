package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.SetMealPageVO;
import com.sky.vo.SetmealDishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    List<Long> findDishesByDishIds(List<Long> ids);

    /**
     * 分页查询
     * @return
     */
    Page<SetMealPageVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量添加
     * @param setmealDishes
     */
    void saveBatch(List<SetmealDish> setmealDishes);

    /**
     * 通过套餐id删除套餐菜品
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id=#{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * get setmealDishes by SetmealId
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id=#{setmealId}")
    List<SetmealDish> getbySetmealId(Long setmealId);

    /**
     * 通过setmealId批量删除
     * @param setmealIds
     */
    void deleteBySetmealIds(List<Long> setmealIds);

    /**
     * get By CategoryId
     * @param categoryId
     * @return
     */
    @Select("select * from setmeal where category_id=#{categoryId}")
    List<Setmeal> getByCategoryId(Long categoryId);

    /**
     * get Setmeal Dishes By SetmealId
     * @param id
     * @return
     */
    List<SetmealDishVO> getSetmealDishesBySetmealId(Long id);
}
