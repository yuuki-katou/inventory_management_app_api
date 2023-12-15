package com.yuuki.katou.inventory_management_app01.controller;

import com.yuuki.katou.inventory_management_app01.dto.BrandsDto;
import com.yuuki.katou.inventory_management_app01.dto.CategoriesDto;
import com.yuuki.katou.inventory_management_app01.dto.ConditionsDto;
import com.yuuki.katou.inventory_management_app01.dto.StoresDto;
import com.yuuki.katou.inventory_management_app01.service.ReferenceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReferenceDataController {

    // 依存関係: ReferenceDataService
    private final ReferenceDataService referenceDataService;

    @Autowired
    public ReferenceDataController(ReferenceDataService referenceDataService) {
        this.referenceDataService = referenceDataService;
    }

    //カテゴリデータを取得
    @GetMapping("/categories")
    public List<CategoriesDto> getCategoryAll() {
        return referenceDataService.categoryFindAll();
    }

    @GetMapping("/brands")
    public List<BrandsDto> getBrandAll() {
        return referenceDataService.brandFindAll();
    }

    @GetMapping("/conditions")
    public List<ConditionsDto> getConditionAll() {
        return referenceDataService.conditionFindAll();
    }

    @GetMapping("/stores")
    public List<StoresDto> getStoreAll() {
        return referenceDataService.storeFindAll();
    }
    
}
