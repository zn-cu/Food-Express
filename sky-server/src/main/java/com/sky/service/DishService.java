package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/**
 * @author 夙落陌上梦
 * @version 1.0
 * @ClassName DishService
 * @DateTime 2025/2/22 上午10:03
 * @Description:
 */

public interface DishService {

    public void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
    //菜品批量删除
    void deleteBatch(List<Long> ids);
    //根据id查询菜品和口味
    DishVO getByIdWithFlavor(Long id);
    //修改菜品
    void updateWithFlavor(DishDTO dishDTO);
    //根据分类id查询菜品
    List<Dish> list(Long categoryId);
    //菜品起售停售
    void startOrStop(Integer status, Long id);
    //条件查询菜品和口味
    List<DishVO> listWithFlavor(Dish dish);
}

