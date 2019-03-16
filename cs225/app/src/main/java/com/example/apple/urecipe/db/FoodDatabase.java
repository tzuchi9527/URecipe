package com.example.apple.urecipe.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.apple.urecipe.dao.FoodDao;
import com.example.apple.urecipe.module.Food;

@Database(entities = {Food.class}, version = 1, exportSchema = false)
public abstract class FoodDatabase extends RoomDatabase {

    private static FoodDatabase instance;
    public abstract FoodDao foodDao();

    public static synchronized FoodDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FoodDatabase.class, "food_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



}
