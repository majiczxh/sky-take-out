package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 添加setmeal
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void save(Setmeal setmeal);

    /**
     * 更新setmeal
     * @param setmeal
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);

    @Select("select * from setmeal where id=#{id}")
    Setmeal getById(Long id);

    /**
     * 通过id批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
