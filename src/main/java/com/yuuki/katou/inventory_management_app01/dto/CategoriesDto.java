package com.yuuki.katou.inventory_management_app01.dto;

public class CategoriesDto {
    private int categoryID;
    private String categoryName;

    public CategoriesDto(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public CategoriesDto() {
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
}
