package com.example.foodallergendetection.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String firstName;
    public String lastName;
    public String email;
    public String password;  // Store password (Note: Ideally, this should be hashed for security)
    public String otp; // This field is for OTP verification
}
