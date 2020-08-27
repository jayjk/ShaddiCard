package com.shaddicard.repositories.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.shaddicard.model.MasterData;

import java.util.List;

@Dao
public interface DataDao {
 
    @Query("SELECT * FROM masterdata")
    List<MasterData> getAllData();
 
    @Insert
    void insert(List<MasterData> data);

    @Query("UPDATE masterdata SET status=:status WHERE uniqueID=:id")
    void update(String status, int id);
 

    
}