package com.example.foodallergendetection.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scanned_items")
public class ScannedItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String barcode;
    public String productName;
    public String expiryDate;
}
