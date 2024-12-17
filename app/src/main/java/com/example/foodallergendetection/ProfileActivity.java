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
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.foodallergendetection.database.Allergy;
import com.example.foodallergendetection.database.AppDatabase;
import com.example.foodallergendetection.database.UserProfile;
import java.util.Arrays;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);


        EditText etName = findViewById(R.id.et_name);
        EditText etEmail = findViewById(R.id.et_email);
        CheckBox cbMilk = findViewById(R.id.cb_milk);
        CheckBox cbPeanuts = findViewById(R.id.cb_peanuts);
        CheckBox cbGluten = findViewById(R.id.cb_gluten);
        Button saveButton = findViewById(R.id.btn_save_profile);

        AppDatabase db = AppDatabase.getInstance(this);

        saveButton.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();

            // Save user profile
            UserProfile user = new UserProfile();
            user.name = name;
            user.email = email;

            new Thread(() -> db.userProfileDao().insertUser(user)).start();

            // Save allergies
            Allergy milk = new Allergy();
            milk.name = "Milk";
            milk.isAllergic = cbMilk.isChecked();

            Allergy peanuts = new Allergy();
            peanuts.name = "Peanuts";
            peanuts.isAllergic = cbPeanuts.isChecked();

            Allergy gluten = new Allergy();
            gluten.name = "Gluten";
            gluten.isAllergic = cbGluten.isChecked();

            new Thread(() -> db.allergyDao().insertAllergies(Arrays.asList(milk, peanuts, gluten))).start();

            Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show();
        });
    }
}
