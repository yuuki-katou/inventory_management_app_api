package com.yuuki.katou.inventory_management_app01.mapper;

import com.yuuki.katou.inventory_management_app01.entity.Brand;
import com.yuuki.katou.inventory_management_app01.entity.Category;
import com.yuuki.katou.inventory_management_app01.entity.Condition;
import com.yuuki.katou.inventory_management_app01.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReferenceDataMapper {

    //全てのカテゴリ情報を取得する
    @Select("SELECT * FROM categories")
    @ResultMap("categoryResultMap")
    List<Category> categoryFindAll();

    @Select("SELECT * FROM brands")
    @ResultMap("brandResultMap")
    List<Brand> brandFindAll();

    @Select("SELECT * FROM conditions")
    @ResultMap("conditionResultMap")
    List<Condition> conditionFindAll();

    @Select("SELECT * FROM stores")
    @ResultMap("storeResultMap")
    List<Store> storeFindAll();
}
