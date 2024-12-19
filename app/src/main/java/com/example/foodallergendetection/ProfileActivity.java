package com.example.foodallergendetection;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.foodallergendetection.database.Allergy;
import com.example.foodallergendetection.database.AppDatabase;
import com.example.foodallergendetection.database.UserProfile;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etEmail;
    private CheckBox cbMilk, cbPeanuts, cbGluten, cbEggs, cbShellfish, cbNuts, cbSoy, cbWheat, cbDairy, cbLactose;
    private Button saveButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        // Initialize UI elements
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        cbMilk = findViewById(R.id.cb_milk);
        cbPeanuts = findViewById(R.id.cb_peanuts);
        cbGluten = findViewById(R.id.cb_gluten);
        cbEggs = findViewById(R.id.cb_eggs);
        cbShellfish = findViewById(R.id.cb_shellfish);
        cbNuts = findViewById(R.id.cb_nuts);
        cbSoy = findViewById(R.id.cb_soy);
        cbWheat = findViewById(R.id.cb_wheat);
        cbDairy = findViewById(R.id.cb_dairy);
        cbLactose = findViewById(R.id.cb_lactose);
        saveButton = findViewById(R.id.btn_save_profile);

        db = AppDatabase.getInstance(this);

        // Load existing user profile and allergies from the database
        loadUserProfile();

        // Save button click listener
        saveButton.setOnClickListener(v -> saveProfile());
    }

    // Save user profile and allergies
    private void saveProfile() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        // Save user profile in a background thread
        new Thread(() -> {
            // Save user profile
            UserProfile user = new UserProfile();
            user.name = name;
            user.email = email;
            db.userProfileDao().insertUser(user);

            // Clear the existing allergies from the database
            db.allergyDao().deleteAllAllergies();

            // Save allergies
            List<Allergy> allergies = getAllergiesFromUI();
            db.allergyDao().insertAllergies(allergies);

            // Show toast on main thread
            runOnUiThread(() -> Toast.makeText(ProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show());
        }).start();
    }

    // Load the saved user profile and allergies from the database
    private void loadUserProfile() {
        new Thread(() -> {
            // Fetch user profile and allergies from the database
            UserProfile user = db.userProfileDao().getUser();
            List<Allergy> allergies = db.allergyDao().getAllAllergies();

            // Check if user profile is found and populate the fields
            runOnUiThread(() -> {
                if (user != null) {
                    etName.setText(user.name);
                    etEmail.setText(user.email);
                }

                // Populate allergies data
                if (allergies != null) {
                    for (Allergy allergy : allergies) {
                        switch (allergy.name) {
                            case "Milk":
                                cbMilk.setChecked(allergy.isAllergic);
                                break;
                            case "Peanuts":
                                cbPeanuts.setChecked(allergy.isAllergic);
                                break;
                            case "Gluten":
                                cbGluten.setChecked(allergy.isAllergic);
                                break;
                            case "Eggs":
                                cbEggs.setChecked(allergy.isAllergic);
                                break;
                            case "Shellfish":
                                cbShellfish.setChecked(allergy.isAllergic);
                                break;
                            case "Nuts":
                                cbNuts.setChecked(allergy.isAllergic);
                                break;
                            case "Soy":
                                cbSoy.setChecked(allergy.isAllergic);
                                break;
                            case "Wheat":
                                cbWheat.setChecked(allergy.isAllergic);
                                break;
                            case "Dairy":
                                cbDairy.setChecked(allergy.isAllergic);
                                break;
                            case "Lactose":
                                cbLactose.setChecked(allergy.isAllergic);
                                break;
                        }
                    }
                }
            });
        }).start();
    }

    // Collect allergies from UI checkboxes
    private List<Allergy> getAllergiesFromUI() {
        return List.of(
                new Allergy("Milk", cbMilk.isChecked()),
                new Allergy("Peanuts", cbPeanuts.isChecked()),
                new Allergy("Gluten", cbGluten.isChecked()),
                new Allergy("Eggs", cbEggs.isChecked()),
                new Allergy("Shellfish", cbShellfish.isChecked()),
                new Allergy("Nuts", cbNuts.isChecked()),
                new Allergy("Soy", cbSoy.isChecked()),
                new Allergy("Wheat", cbWheat.isChecked()),
                new Allergy("Dairy", cbDairy.isChecked()),
                new Allergy("Lactose", cbLactose.isChecked())
        );
    }

}
