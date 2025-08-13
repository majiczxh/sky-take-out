package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishCategoryDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(value = OperationType.INSERT)
    void save(Dish dish);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select * from dish where id=#{id}")
    Dish findById(Long id);

    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据分类id查询菜品(status=1)
     * @param categoryId
     * @return
     */
    @Select("select * from dish where category_id=#{categoryId} and status=1")
    List<Dish> getByCategoryId(Long categoryId);

    /**
     * 根据分类id查询菜品(status=1),附加categoryName
     * @param categoryId
     * @return
     */
    List<DishCategoryDTO> getDishesWithCategoryInfoByCategoryId(Long categoryId);
}
