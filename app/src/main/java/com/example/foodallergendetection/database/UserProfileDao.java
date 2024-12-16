package com.example.foodallergendetection.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserProfileDao {
    @Insert
    void insertUser(UserProfile user);

    @Update
    void updateUser(UserProfile user);

    @Query("SELECT * FROM user_profile LIMIT 1")
    UserProfile getUser();
}
