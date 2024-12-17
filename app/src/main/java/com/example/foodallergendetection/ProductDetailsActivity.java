package com.example.foodallergendetection;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodallergendetection.database.Allergy;
import com.example.foodallergendetection.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://world.openfoodfacts.org/api/v2/";
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialize the database instance
        db = AppDatabase.getInstance(this);

        // Get Barcode Value from Intent
        Intent intent = getIntent();
        //String barcode = intent.getStringExtra("BARCODE_VALUE");
        String barcode = "7622210449283"; // Example barcode for testing purposes

        // Handle invalid barcode
        if (barcode == null || barcode.isEmpty()) {
            Toast.makeText(this, "Invalid barcode. Please try again.", Toast.LENGTH_SHORT).show();
            return; // Exit if no barcode is provided
        }

        // Fetch product details from Open Food API
        fetchProductDetails(barcode);
    }

    private void fetchProductDetails(String barcode) {
        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodApi openFoodApi = retrofit.create(OpenFoodApi.class);

        // Make API call
        openFoodApi.getProductByBarcode(barcode).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Product product = response.body().getProduct();
                    Log.d("ProductDetails", "API Response: " + response.body());
                    Log.d("ProductDetails", "API Response: " + response.body().toString());
                    Log.d("ProductDetails", "API Response: " + response.body().getProduct());


                    // Log the full response for debugging
                    if (product != null) {
                        String productName = product.getProductName();
                        String ingredients = product.getIngredientsText();

                        // Log the ingredients for debugging
                        if (ingredients != null && !ingredients.isEmpty()) {
                            // Display product details (ingredients)
                            TextView tvProductTitle = findViewById(R.id.tvProductTitle);
                            TextView tvProductIngredients = findViewById(R.id.tvProductIngredients);
                            TextView tvAllergyAlert = findViewById(R.id.tvAllergyAlert);

                            tvProductTitle.setText(productName);
                            tvProductIngredients.setText("Ingredients: " + ingredients);
                            // Now, compare allergies with the ingredients
                            compareAllergiesWithProduct(ingredients, tvAllergyAlert);
                        } else {
                            Toast.makeText(ProductDetailsActivity.this, "No ingredients available for this product.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ProductDetailsActivity.this, "Product not found.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Failed to load product details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void compareAllergiesWithProduct(String ingredient, TextView tvAllergyAlert) {
        new Thread(() -> {
            try {
                // Fetch user allergies from the database
                List<Allergy> allergies = db.allergyDao().getAllAllergies();

                // List to hold allergies found in product
                List<String> foundAllergies = new ArrayList<>();
                String ingredients = "Milk, Soy, Gluten";// testing sample data

                // Check for matching allergies
                for (Allergy allergy : allergies) {
                    if (ingredients != null && ingredients.contains(allergy.name)) {
                        foundAllergies.add(allergy.name);
                    }
                }

                // Display the allergies found in the product
                runOnUiThread(() -> {
                    if (!foundAllergies.isEmpty()) {
                        tvAllergyAlert.setText("This product contains the following allergens: " + String.join(", ", foundAllergies));
                        tvAllergyAlert.setVisibility(View.VISIBLE);
                    } else {
                        tvAllergyAlert.setText("This product does not contain any of your allergies.");
                        tvAllergyAlert.setVisibility(View.VISIBLE);
                    }
                });
            } catch (Exception e) {
                Log.e("ProductDetails", "Error comparing allergies: ", e);
            }
        }).start();
    }
}