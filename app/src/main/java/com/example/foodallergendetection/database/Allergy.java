package com.example.foodallergendetection.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "allergies")
public class Allergy {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;  // Allergy name, e.g., "Milk"
    public boolean isAllergic; // Whether the user is allergic to this
}
