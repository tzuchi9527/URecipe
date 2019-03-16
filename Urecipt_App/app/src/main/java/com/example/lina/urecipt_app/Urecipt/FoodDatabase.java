package com.example.lina.urecipt_app.Urecipt;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.lina.urecipt_app.Urecipt.dao.FoodDao;
import com.example.lina.urecipt_app.Urecipt.models.Food;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



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
