package com.example.foodallergendetection;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_product_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvProductTitle = findViewById(R.id.tvProductTitle);
        TextView tvProductIngredients = findViewById(R.id.tvProductIngredients);
        TextView tvAllergyAlert = findViewById(R.id.tvAllergyAlert);

        // Get Barcode Value from Intent
        Intent intent = getIntent();
        String barcode = intent.getStringExtra("BARCODE_VALUE");

        // Simulated Product Details (Replace with API integration)
        String productName = "Sample Product"; // Replace with actual product name from API
        String ingredients = "Milk, Wheat, Soy"; // Replace with actual ingredients from API
        boolean containsAllergen = ingredients.contains("Milk"); // Replace with dynamic allergen check

        // Display Details
        tvProductTitle.setText(productName);
        tvProductIngredients.setText("Ingredients: " + ingredients);
        if (containsAllergen) {
            tvAllergyAlert.setText("Alert: Contains allergens!");
            tvAllergyAlert.setVisibility(View.VISIBLE);
        } else {
            tvAllergyAlert.setVisibility(View.GONE);
        }

        // Back Button Listener
        findViewById(R.id.btnBackToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to Home Screen
                Intent homeIntent = new Intent(activity_product_details.this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }
}