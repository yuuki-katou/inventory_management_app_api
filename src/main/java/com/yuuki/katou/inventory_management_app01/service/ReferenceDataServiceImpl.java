package com.yuuki.katou.inventory_management_app01.service;

import com.yuuki.katou.inventory_management_app01.dto.BrandsDto;
import com.yuuki.katou.inventory_management_app01.dto.CategoriesDto;
import com.yuuki.katou.inventory_management_app01.dto.ConditionsDto;
import com.yuuki.katou.inventory_management_app01.dto.StoresDto;
import com.yuuki.katou.inventory_management_app01.entity.Brand;
import com.yuuki.katou.inventory_management_app01.entity.Category;
import com.yuuki.katou.inventory_management_app01.entity.Condition;
import com.yuuki.katou.inventory_management_app01.entity.Store;
import com.yuuki.katou.inventory_management_app01.exception.ResourceNotFoundException;
import com.yuuki.katou.inventory_management_app01.mapper.ReferenceDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {


    private final ReferenceDataMapper referenceMapper;

    @Autowired
    public ReferenceDataServiceImpl(ReferenceDataMapper referenceMapper) {
        this.referenceMapper = referenceMapper;
    }

    //全てのカテゴリ情報を取得する
    @Override
    public List<CategoriesDto> categoryFindAll() {
        //
        List<Category> categories = referenceMapper.categoryFindAll();

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("カテゴリ情報がみつかりませんでした。");
        } else {
            return categories.stream()
                    .map(Category::convertToCategoriesDto).toList();
        }
    }

    @Override
    public List<BrandsDto> brandFindAll() {
        List<Brand> brands = referenceMapper.brandFindAll();

        if (brands.isEmpty()) {
            throw new ResourceNotFoundException("ブランド情報が見つかりませんでした。");
        } else {
            return brands.stream()
                    .map(Brand::convertToBrandDto).toList();
        }
    }

    @Override
    public List<ConditionsDto> conditionFindAll() {
        List<Condition> conditions = referenceMapper.conditionFindAll();

        if (conditions.isEmpty()) {
            throw new ResourceNotFoundException("商品の状態情報が見つかりませんでした。");
        } else {
            return conditions.stream()
                    .map(Condition::convertToConditionDto).toList();
        }
    }

    @Override
    public List<StoresDto> storeFindAll() {
        List<Store> stores = referenceMapper.storeFindAll();

        if (stores.isEmpty()) {
            throw new ResourceNotFoundException("店舗情報が見つかりませんでした。");
        } else {
            return stores.stream()
                    .map(Store::convertToStoreDto).toList();
        }
    }
}
