package com.shaddicard.model;

import androidx.room.ColumnInfo;

public class Id {

    @ColumnInfo(name = "idName")
    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}