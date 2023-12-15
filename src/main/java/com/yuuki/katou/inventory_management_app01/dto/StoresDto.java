package com.yuuki.katou.inventory_management_app01.dto;

public class StoresDto {
    private int storeID;
    private String storeName;
    private String location;
    private String telephoneNumber;

    public StoresDto(int storeID, String storeName, String location, String telephoneNumber) {
        this.storeID = storeID;
        this.storeName = storeName;
        this.location = location;
        this.telephoneNumber = telephoneNumber;
    }

    public StoresDto() {
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
}
