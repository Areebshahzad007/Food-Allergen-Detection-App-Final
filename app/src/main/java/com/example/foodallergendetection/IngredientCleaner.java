package com.example.foodallergendetection;

import java.util.ArrayList;
import java.util.List;

public class IngredientCleaner {

    // Function to clean and normalize the ingredients string
    public static List<String> cleanIngredients(String ingredients) {
        // Normalize the ingredients by replacing underscores with spaces and converting to lowercase
        String normalized = ingredients.toLowerCase().replace("_", " ");

        // Remove percentages (e.g., 50.7%)
        normalized = normalized.replaceAll("\\d+%?", "");

        // Remove anything inside parentheses (additives, oils, etc.)
        normalized = normalized.replaceAll("\\(.*?\\)", "");

        // Remove non-relevant words like 'may contain', 'low-fat', 'skimmed', etc.
        normalized = normalized.replaceAll("may contain|low-fat|skimmed", "");

        // Split the string by commas to get individual ingredients
        String[] ingredientArray = normalized.split(",");

        // Clean each ingredient by trimming spaces and removing unwanted words
        List<String> cleanedIngredients = new ArrayList<>();
        for (String ingredient : ingredientArray) {
            String cleaned = ingredient.trim();  // Remove extra spaces

            // Optionally, remove any unwanted terms like 'flavourings', 'emulsifiers', etc.
            cleaned = cleaned.replaceAll("flavourings|emulsifiers|raising agents", "");

            // If ingredient is non-empty, add it to the list
            if (!cleaned.isEmpty()) {
                cleanedIngredients.add(cleaned);
            }
        }

        return cleanedIngredients;
    }
}
