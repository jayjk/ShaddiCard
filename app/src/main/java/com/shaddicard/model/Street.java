package com.shaddicard.model;

import androidx.room.ColumnInfo;

public class Street {

    private String number;

    @ColumnInfo(name = "streetName")
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}