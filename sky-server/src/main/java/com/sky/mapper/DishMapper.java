package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {


    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    //新增菜品
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    //分页查询
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //通过id查询菜品
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);
    /*
     * 根据id删除菜品表中的数据
     * */
    void deleteById(List<Long> ids);
    //修改菜品数据，启用停用也可以用这个
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);
    //根据分类id查询菜品数据，动态查询，前端可以根据名字，分类查询，所以传入的是dish对象，传递多个参数
    List<Dish> list(Dish dish);

    //根据套餐id查询菜品，得连表查询，从套餐菜品关系表中获取菜品id再查询
    @Select("select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long id);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);

}

