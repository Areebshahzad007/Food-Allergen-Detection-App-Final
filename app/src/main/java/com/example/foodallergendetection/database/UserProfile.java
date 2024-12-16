package com.example.foodallergendetection.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;
}
