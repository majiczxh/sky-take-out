package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetMealPageVO {

    private Long id;                // 套餐ID
    private Long categoryId;        // 分类ID
    private String name;            // 套餐名称
    private BigDecimal price;       // 套餐价格（建议用BigDecimal处理金额）
    private Integer status;         // 状态（0停售 1启售）
    private String description;     // 描述
    private String image;           // 图片URL
    private String updateTime;      // 更新时间（String类型，前端展示用）
    private String categoryName;    // 分类名称（关联查询字段）

}