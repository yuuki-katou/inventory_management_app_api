package com.yuuki.katou.inventory_management_app01.dto;

public class ConditionsDto {
    private int conditionID;
    private String conditionName;

    public ConditionsDto(int conditionID, String conditionName) {
        this.conditionID = conditionID;
        this.conditionName = conditionName;
    }

    public ConditionsDto() {
    }

    public int getConditionID() {
        return conditionID;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionID(int conditionID) {
        this.conditionID = conditionID;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }
}
