package com.yuuki.katou.inventory_management_app01.entity;

import com.yuuki.katou.inventory_management_app01.dto.StoresDto;
import org.modelmapper.ModelMapper;

public class Store {
    private int storeID;
    private String storeName;
    private String location;
    private String telephoneNumber;

    public Store(int storeID, String storeName, String location, String telephoneNumber) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.location = location;
        this.telephoneNumber = telephoneNumber;
    }

    public Store() {
    }

    public int getStoreID() {
        return storeID;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLocation() {
        return location;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public StoresDto convertToStoreDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, StoresDto.class);
    }
}


