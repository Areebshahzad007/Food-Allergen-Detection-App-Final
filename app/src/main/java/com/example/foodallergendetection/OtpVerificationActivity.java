package com.example.foodallergendetection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodallergendetection.database.AppDatabase;
import com.example.foodallergendetection.database.User;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerifyOtp;
    private String correctOtp;
    private String email;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Log.d("ProductDetails", "inside opt verification  -->: ");
        etOtp = findViewById(R.id.et_otp);
        btnVerifyOtp = findViewById(R.id.btn_verify_otp);
        db = AppDatabase.getInstance(this);

        // Get OTP and email passed from SignupActivity
        correctOtp = getIntent().getStringExtra("OTP");
        email = getIntent().getStringExtra("email");

        btnVerifyOtp.setOnClickListener(v -> {
            String enteredOtp = etOtp.getText().toString();
            if (enteredOtp.equals(correctOtp)) {
                // OTP verified, save user data in the database
                saveUserData();
            } else {
                Toast.makeText(OtpVerificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData() {
        // Save the user in the database after OTP verification
        User user = new User();
        user.email = email;

        new Thread(() -> {
            db.userDao().insertUser(user);
            runOnUiThread(() -> {
                Toast.makeText(OtpVerificationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(OtpVerificationActivity.this, LoginActivity.class));
                finish();
            });
        }).start();
    }
}
