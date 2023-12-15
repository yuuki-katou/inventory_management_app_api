package com.yuuki.katou.inventory_management_app01.dto;

public class BrandsDto {

    private String brandName;

    private String address;

    private String phoneNumber;

    public BrandsDto(String brandName, String address, String phoneNumber) {
        this.brandName = brandName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public BrandsDto() {
    }

    public String getBrandName() {
        return brandName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
