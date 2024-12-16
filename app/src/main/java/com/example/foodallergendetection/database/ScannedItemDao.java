package com.example.foodallergendetection.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface ScannedItemDao {
    @Insert
    void insertScannedItem(ScannedItem item);

    @Query("SELECT * FROM scanned_items")
    List<ScannedItem> getAllScannedItems();

    @Delete
    void deleteItem(ScannedItem item);
}
