package com.example.foodallergendetection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.foodallergendetection.database.AppDatabase;
import com.example.foodallergendetection.database.User;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText etOtp;
    private Button btnVerifyOtp;
    private String correctOtp;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        Log.d("FAD", "Inside OTP Verification Activity");

        etOtp = findViewById(R.id.et_otp);
        btnVerifyOtp = findViewById(R.id.btn_verify_otp);
        db = AppDatabase.getInstance(this);

        // Get OTP and other user data passed from SignupActivity
        correctOtp = getIntent().getStringExtra("OTP");
        email = getIntent().getStringExtra("email");
        firstName = getIntent().getStringExtra("fname");
        lastName = getIntent().getStringExtra("lname");
        password = getIntent().getStringExtra("pwd");

        btnVerifyOtp.setOnClickListener(v -> {
            String enteredOtp = etOtp.getText().toString();
            if (enteredOtp.equals(correctOtp)) {
                // OTP verified, save user data in the database
                Log.d("FAD", "OTP verified successfully");
                saveUserData();
            } else {
                Toast.makeText(OtpVerificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData() {
        // Save the user in the database after OTP verification
        User user=new User(firstName, lastName, email, password);


        new Thread(() -> {
            try {
                db.userDao().insertUser(user);
                runOnUiThread(() -> {
                    Toast.makeText(OtpVerificationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    // Navigate to LoginActivity
                    startActivity(new Intent(OtpVerificationActivity.this, LoginActivity.class));
                    finish();
                });
            } catch (Exception e) {
                Log.e("FAD", "Error saving user: ", e);
                runOnUiThread(() ->
                        Toast.makeText(OtpVerificationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
