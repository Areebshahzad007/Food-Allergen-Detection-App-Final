package com.example.foodallergendetection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodallergendetection.database.AppDatabase;
import com.example.foodallergendetection.database.SendGridHelper;

import java.io.IOException;
import java.util.Random;

public class SignupActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword;
    private Button btnSendOtp;
    private Button btnlogin;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnSendOtp = findViewById(R.id.btn_send_otp);
        //btnlogin = findViewById(R.id.btn_login_redirect);

        db = AppDatabase.getInstance(this);

        btnSendOtp.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                // Basic email validation (optional but recommended)
//                if (!isValidEmail(email)) {
//                    Toast.makeText(SignupActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                // Simulate OTP generation
                String otp = generateOtp();
                Log.d("fad", "otp -->: " + otp);

                // Send OTP email using SendGrid (run in a background thread)
                sendOtpInBackground(email, otp);

                // Pass OTP and email to OTP verification activity
                Intent intent = new Intent(SignupActivity.this, OtpVerificationActivity.class);
                intent.putExtra("OTP", otp); // Pass OTP for verification
                intent.putExtra("fname", firstName); // Pass email for OTP verification
                intent.putExtra("lname", lastName); // Pass email for OTP verification
                intent.putExtra("email", email); // Pass email for OTP verification
                intent.putExtra("pwd", password); // Pass email for OTP verification
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }

        });
//        // Set onClick listener for the "Sign Up" redirect button
//        btnlogin.setOnClickListener(v -> {
//            // Redirect to SignUpActivity when clicked
//            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
//            startActivity(intent);
//        });
    }

    // Simulate OTP generation
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a random 6-digit number
        return String.valueOf(otp);
    }
    // Email format validation
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    // Send OTP in a background thread
    private void sendOtpInBackground(String email, String otp) {
        new Thread(() -> {
                SendGridHelper.sendOtpEmail(email, otp);
        }).start();
    }
}
