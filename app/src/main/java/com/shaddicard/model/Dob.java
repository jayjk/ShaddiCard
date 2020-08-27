package com.shaddicard.model;

import androidx.room.ColumnInfo;

public class Dob {

    @ColumnInfo(name = "dateofbirth")
    private String date;

    @ColumnInfo(name = "candidateAge")
    private String age;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}