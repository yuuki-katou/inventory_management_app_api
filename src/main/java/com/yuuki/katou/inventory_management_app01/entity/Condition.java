package com.yuuki.katou.inventory_management_app01.entity;

import com.yuuki.katou.inventory_management_app01.dto.ConditionsDto;
import org.modelmapper.ModelMapper;

public class Condition {

    private int conditionID;
    private String conditionName;

    public Condition(int conditionID, String conditionName) {
        this.conditionID = conditionID;
        this.conditionName = conditionName;
    }

    public Condition() {
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

    public ConditionsDto convertToConditionDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, ConditionsDto.class);
    }
}

