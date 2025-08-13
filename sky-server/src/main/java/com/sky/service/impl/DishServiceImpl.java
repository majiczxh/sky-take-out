package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishCategoryDTO;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    @Transactional
    public void addDishWithFlavor(DishDTO dishDTO) {
        //add a dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.save(dish);

        //add flavors
        Long id=dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null && flavors.size()>0){
            flavors.forEach(flavor->{
                flavor.setDishId(id);
            });
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> records=dishMapper.pageQuery(dishPageQueryDTO);
        PageResult pageResult = new PageResult(records.getTotal(),records.getResult());
        return pageResult;
    }

    @Override
    @Transactional
    public void delete(List<Long> ids) {
        //查询状态，在售status=1不可删除
        for (Long id : ids) {
            if(dishMapper.findById(id).getStatus() == 1){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //与套餐关联不可删除
        List<Long> setMeal=setMealDishMapper.findDishesByDishIds(ids);
        log.info("dishIdsInSetMeal:{}",setMeal);
        if(setMeal.size()>0 && setMeal!=null){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除dish
//        for(Long id:ids){
//            dishMapper.deleteById(id);
//            //删除flavor
//            dishFlavorMapper.deleteByDishId(id);
//        }
        dishMapper.deleteByIds(ids);
        //删除flavor
        dishFlavorMapper.deleteByDishIds(ids);


    }

    @Override
    public DishVO getById(Long id) {
        Dish dish=dishMapper.findById(id);
        List<DishFlavor> dishFlavors=dishFlavorMapper.getByDishId(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    @Override
    @Transactional
    public void updateDishWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteByDishId(dish.getId());
        List<DishFlavor> dishFlavors=dishDTO.getFlavors();
        if(dishFlavors!=null&&dishFlavors.size()>0){
            for(DishFlavor dishFlavor:dishFlavors){
                dishFlavor.setDishId(dishDTO.getId());
            }
            dishFlavorMapper.insertBatch(dishFlavors);
        }
        log.info("update done");

    }

    @Override
    public List<Dish> queryByCategoryId(Long categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }

    /**
     * 根据分类id查询菜品及其口味
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> queryWithFlavorsByCategoryId(Long categoryId) {
        List<DishCategoryDTO> dishList=dishMapper.getDishesWithCategoryInfoByCategoryId(categoryId);
        List<DishVO> dishVOList=new ArrayList<>();
        if(dishList!=null&&dishList.size()>0){
            for(DishCategoryDTO dishCategoryDTO:dishList){
                DishVO dishVO=new DishVO();
                BeanUtils.copyProperties(dishCategoryDTO,dishVO);
                dishVO.setFlavors(dishFlavorMapper.getByDishId(dishCategoryDTO.getId()));
                dishVOList.add(dishVO);
            }
            return dishVOList;
        }
        return null;
    }
}
