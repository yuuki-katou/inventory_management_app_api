package com.yuuki.katou.inventory_management_app01.entity;

import com.yuuki.katou.inventory_management_app01.dto.ProductDto;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private Integer productId;
    private String productName;
    private String modelNumber;
    private Integer brandId;
    private Integer categoryId;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer conditionId;

    private String description;
    private String image;
    private Integer storeId;
    private LocalDateTime dateAdded;
    private LocalDateTime lastUpdated;

    // 関連するエンティティ
    private String brand;
    private String category;
    private String condition;
    private String store;

    private String storeLocation;

    private String storeTelephoneNumber;

    public Product() {
    }

    public Product(Integer productId, String productName, String modelNumber, Integer brandId, Integer categoryId, BigDecimal price, Integer stockQuantity, Integer conditionId, String description, String image, Integer storeId, LocalDateTime dateAdded, LocalDateTime lastUpdated, String brand, String category, String condition, String store, String storeLocation, String storeTelephoneNumber) {
        this.productId = productId;
        this.productName = productName;
        this.modelNumber = modelNumber;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.conditionId = conditionId;
        this.description = description;
        this.image = image;
        this.storeId = storeId;
        this.dateAdded = dateAdded;
        this.lastUpdated = lastUpdated;
        this.brand = brand;
        this.category = category;
        this.condition = condition;
        this.store = store;
        this.storeLocation = storeLocation;
        this.storeTelephoneNumber = storeTelephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName) && Objects.equals(modelNumber, product.modelNumber) && Objects.equals(brandId, product.brandId) && Objects.equals(categoryId, product.categoryId) && Objects.equals(price, product.price) && Objects.equals(stockQuantity, product.stockQuantity) && Objects.equals(conditionId, product.conditionId) && Objects.equals(description, product.description) && Objects.equals(image, product.image) && Objects.equals(storeId, product.storeId) && Objects.equals(dateAdded, product.dateAdded) && Objects.equals(lastUpdated, product.lastUpdated) && Objects.equals(brand, product.brand) && Objects.equals(category, product.category) && Objects.equals(condition, product.condition) && Objects.equals(store, product.store) && Objects.equals(storeLocation, product.storeLocation) && Objects.equals(storeTelephoneNumber, product.storeTelephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, modelNumber, brandId, categoryId, price, stockQuantity, conditionId, description, image, storeId, dateAdded, lastUpdated, brand, category, condition, store, storeLocation, storeTelephoneNumber);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", modelNumber='" + modelNumber + '\'' +
                ", brandId=" + brandId +
                ", categoryId=" + categoryId +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", conditionId=" + conditionId +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", storeId=" + storeId +
                ", dateAdded=" + dateAdded +
                ", lastUpdated=" + lastUpdated +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", condition='" + condition + '\'' +
                ", store='" + store + '\'' +
                ", storeLocation='" + storeLocation + '\'' +
                ", storeTelephoneNumber='" + storeTelephoneNumber + '\'' +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public Integer getConditionId() {
        return conditionId;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getCondition() {
        return condition;
    }

    public String getStore() {
        return store;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getStoreTelephoneNumber() {
        return storeTelephoneNumber;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setConditionId(Integer conditionId) {
        this.conditionId = conditionId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public void setStoreTelephoneNumber(String storeTelephoneNumber) {
        this.storeTelephoneNumber = storeTelephoneNumber;
    }

    public ProductDto convertToProductDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ProductDto.class);
    }
}
