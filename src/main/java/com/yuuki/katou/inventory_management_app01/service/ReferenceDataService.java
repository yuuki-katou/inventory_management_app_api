package com.yuuki.katou.inventory_management_app01.service;

import com.yuuki.katou.inventory_management_app01.dto.BrandsDto;
import com.yuuki.katou.inventory_management_app01.dto.CategoriesDto;
import com.yuuki.katou.inventory_management_app01.dto.ConditionsDto;
import com.yuuki.katou.inventory_management_app01.dto.StoresDto;

import java.util.List;

public interface ReferenceDataService {
    //全てのカテゴリ情報を取得する
    List<CategoriesDto> categoryFindAll();

    List<BrandsDto> brandFindAll();

    List<ConditionsDto> conditionFindAll();

    List<StoresDto> storeFindAll();
}
