package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Set;

/**
 * @author 夙落陌上梦
 * @version 1.0
 * @ClassName DishController
 * @DateTime 2025/2/22 上午9:57
 * @Description:
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")

public class DishController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    @CacheEvict(cacheNames = "dishCache",key = "#dishDTO.categoryId")//key: setmealCache::100
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品,{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询中{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        log.info("菜品批量删除中{}",ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
    //根据id查询菜品
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品,{}",id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
    //修改菜品
    @PutMapping
    @ApiOperation("修改菜品")
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品中，{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        return Result.success();
    }
    //根据分类id查询菜品
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        log.info("根据分类id查询菜品，{}",categoryId);
        List<Dish> dishList =dishService.list(categoryId);
        return Result.success(dishList);
    }

    //菜品起售停售
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    @CacheEvict(cacheNames = "dishCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        log.info("菜品起售停售中,{},{}",status,id);
        dishService.startOrStop(status,id);
        return Result.success();
    }
}
