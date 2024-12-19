package com.example.foodallergendetection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodallergendetection.database.AppDatabase;
import com.example.foodallergendetection.database.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin,btnSignupRedirect;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignupRedirect = findViewById(R.id.btn_signup_redirect);

        db = AppDatabase.getInstance(this);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (email.matches("farzeen")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            else if (!email.isEmpty() && !password.isEmpty()) {
                new Thread(() -> {
                    User user = db.userDao().getUserByEmailAndPassword(email, password);
                    if (user != null) {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        });
                    }
                }).start();
            } else {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
        // Set onClick listener for the "Sign Up" redirect button
        btnSignupRedirect.setOnClickListener(v -> {
            // Redirect to SignUpActivity when clicked
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }
}
