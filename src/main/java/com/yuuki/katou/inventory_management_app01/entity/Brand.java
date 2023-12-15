package com.yuuki.katou.inventory_management_app01.entity;

import com.yuuki.katou.inventory_management_app01.dto.BrandsDto;
import org.modelmapper.ModelMapper;

public class Brand {
    private String brandName;

    private String address;

    private String phoneNumber;

    public Brand(String brandName, String address, String phoneNumber) {
        this.brandName = brandName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Brand() {
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

    public BrandsDto
    convertToBrandDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, BrandsDto.class);
    }
}

