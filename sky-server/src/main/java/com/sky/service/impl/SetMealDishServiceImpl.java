package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealDishService;
import com.sky.vo.SetMealPageVO;
import com.sky.vo.SetmealDishVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetMealDishServiceImpl implements SetMealDishService {
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Autowired
    private SetmealMapper  setmealMapper;

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetMealPageVO> setMealPageVOPage=setMealDishMapper.pageQuery(setmealPageQueryDTO);
        PageResult records=new PageResult(setMealPageVOPage.getTotal(),setMealPageVOPage.getResult());

        return records;
    }

    @Override
    @Transactional
    public void saveWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.save(setmeal);
        Long id=setmeal.getId();
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0){
            for(SetmealDish setmealDish:setmealDishes){
                setmealDish.setSetmealId(id);
            }
            setMealDishMapper.saveBatch(setmealDishes);
        }
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal=new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    @Override
    @Transactional
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.update(setmeal);
        setMealDishMapper.deleteBySetmealId(setmealDTO.getId());
        List<SetmealDish> setmealDishes=setmealDTO.getSetmealDishes();
        if(setmealDishes!=null&&setmealDishes.size()>0){
            for(SetmealDish setmealDish:setmealDishes){
                setmealDish.setSetmealId(setmealDTO.getId());
            }
            setMealDishMapper.saveBatch(setmealDishes);
        }
    }

    @Override
    public SetmealVO getSetmealWithDishesById(Long id) {
        SetmealVO setmealVO=new SetmealVO();
        Setmeal setmeal=setmealMapper.getById(id);
        List<SetmealDish> setmealDishes=setMealDishMapper.getbySetmealId(id);
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @Transactional
    public void deleteSetmealsWithSetmealDishesByIds(List<Long> ids) {
        //起售状态不可删除
        for(Long id:ids){
            Setmeal setmeal=setmealMapper.getById(id);
            if(setmeal.getStatus()==1){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //删除setmeal
        setmealMapper.deleteBatch(ids);
        //删除setmeal_dish
        setMealDishMapper.deleteBySetmealIds(ids);
    }

    @Override
    public List<Setmeal> getByCategoryId(Long categoryId) {
        List<Setmeal> setmeals=setMealDishMapper.getByCategoryId(categoryId);
        return  setmeals;
    }

    /**
     * 通过套餐id查询套餐中的菜品
     * @param id
     * @return
     */
    @Override
    public List<SetmealDishVO> getSetmealDishesById(Long id) {
        return setMealDishMapper.getSetmealDishesBySetmealId(id);
    }
}
