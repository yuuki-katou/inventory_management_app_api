package com.yuuki.katou.inventory_management_app01.entity;

import com.yuuki.katou.inventory_management_app01.dto.CategoriesDto;
import org.modelmapper.ModelMapper;

public class Category {
    private int categoryID;
    private String categoryName;

    public Category(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoriesDto convertToCategoriesDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, CategoriesDto.class);
    }
}

