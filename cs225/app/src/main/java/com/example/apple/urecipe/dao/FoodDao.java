package com.example.apple.urecipe.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.apple.urecipe.module.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("DELETE FROM food_table")
    void deleteAllFood();

    @Query("SELECT * FROM food_table ORDER BY priority DESC")
    List<Food> getAllFood();

//    @Query("SELECT * FROM food_table WHERE name = :name")
//    LiveData<Food> searchFoodByName (String name);
}
