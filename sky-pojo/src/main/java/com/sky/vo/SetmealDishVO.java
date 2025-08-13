package com.sky.vo;


import com.sky.entity.DishFlavor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDishVO {
    //菜品名称
    private String name;
    //图片
    private String image;
    //描述信息
    private String description;

    private Integer copies;
}
