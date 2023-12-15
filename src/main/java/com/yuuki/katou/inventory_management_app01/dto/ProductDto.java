package com.yuuki.katou.inventory_management_app01.dto;


import com.yuuki.katou.inventory_management_app01.entity.Product;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductDto {
    private int productId;
    private String productName;
    private String modelNumber;
    private String brand;
    private String category;
    private BigDecimal price;
    private int stockQuantity;
    private String condition;
    private String description;
    private String image;
    private String store;
    private String storeLocation;

    private String storeTelephoneNumber;
    private String dateAdded;
    private String lastUpdated;

    public ProductDto() {
    }

    public ProductDto(int productId, String productName, String modelNumber, String brand, String category, BigDecimal price, int stockQuantity, String condition, String description, String image, String store, String storeLocation, String storeTelephoneNumber, String dateAdded, String lastUpdated) {
        this.productId = productId;
        this.productName = productName;
        this.modelNumber = modelNumber;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.condition = condition;
        this.description = description;
        this.image = image;
        this.store = store;
        this.storeLocation = storeLocation;
        this.storeTelephoneNumber = storeTelephoneNumber;
        this.dateAdded = dateAdded;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return productId == that.productId && stockQuantity == that.stockQuantity && Objects.equals(productName, that.productName) && Objects.equals(modelNumber, that.modelNumber) && Objects.equals(brand, that.brand) && Objects.equals(category, that.category) && Objects.equals(price, that.price) && Objects.equals(condition, that.condition) && Objects.equals(description, that.description) && Objects.equals(image, that.image) && Objects.equals(store, that.store) && Objects.equals(storeLocation, that.storeLocation) && Objects.equals(storeTelephoneNumber, that.storeTelephoneNumber) && Objects.equals(dateAdded, that.dateAdded) && Objects.equals(lastUpdated, that.lastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, modelNumber, brand, category, price, stockQuantity, condition, description, image, store, storeLocation, storeTelephoneNumber, dateAdded, lastUpdated);
    }


    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
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

    public String getDateAdded() {
        return dateAdded;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Product convertToEntity() {
        ModelMapper modelMapper = new ModelMapper();
        //曖昧な型変換は無視される　参考URL :https://stackoverflow.com/questions/49831753/modelmapper-matches-multiple-source-property-hierarchies）
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper.map(this, Product.class);
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", modelNumber='" + modelNumber + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", condition='" + condition + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", store='" + store + '\'' +
                ", storeLocation='" + storeLocation + '\'' +
                ", storeTelephoneNumber='" + storeTelephoneNumber + '\'' +
                ", dateAdded='" + dateAdded + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                '}';
    }
}

