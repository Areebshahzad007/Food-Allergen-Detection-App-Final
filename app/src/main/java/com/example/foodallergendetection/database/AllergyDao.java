package com.example.foodallergendetection.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AllergyDao {
    @Insert
    void insertAllergies(List<Allergy> allergies);

    @Update
    void updateAllergy(Allergy allergy);

    @Query("SELECT * FROM allergies")
    List<Allergy> getAllAllergies();

    // Add this new method to delete all allergies
    @Query("DELETE FROM allergies")
    void deleteAllAllergies();
}
