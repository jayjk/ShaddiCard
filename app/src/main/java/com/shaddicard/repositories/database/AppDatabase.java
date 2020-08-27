package com.shaddicard.repositories.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.shaddicard.model.MasterData;

@Database(entities = {MasterData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}