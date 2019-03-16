//package com.example.lina.urecipt_app.Urecipt.dao;
//
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Delete;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Update;
//
//import android.arch.persistence.room.Query;
//
//import com.example.lina.urecipt_app.Urecipt.models.Item;
//
//import java.util.List;
//
//
//// test only
//
//@Dao
//public interface ItemDAO {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)  // or OnConflictStrategy.IGNORE
//    void insert(Item item);
//
//    @Update
//    void update(Item item);
//
//    @Delete
//    void delete(Item item);
//
//    @Query("SELECT * FROM Item")
//    List<Item> getItems();
//
//    @Query("SELECT * FROM Item WHERE id = :id")
//    Item getItemById(Long id);
//}
