package com.example.foodallergendetection.database;
import android.util.Log;

import com.example.foodallergendetection.database.SendGridEmailRequest;
import com.example.foodallergendetection.database.SendGridService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendGridHelper {

    private static final String BASE_URL = "https://api.sendgrid.com/v3/";

    public static void sendOtpEmail(String email, String otp) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SendGridService service = retrofit.create(SendGridService.class);

        SendGridEmailRequest.Personalization personalization = new SendGridEmailRequest.Personalization(new String[]{email}, "OTP Verification");
        SendGridEmailRequest.Content content = new SendGridEmailRequest.Content("text/plain", "Your OTP is: " + otp);

        SendGridEmailRequest request = new SendGridEmailRequest(
                new SendGridEmailRequest.Personalization[]{personalization},
                new SendGridEmailRequest.From("from_email@example.com"),
                new SendGridEmailRequest.Content[]{content}
        );

        service.sendEmail(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("SendGrid", "OTP sent successfully");
                } else {
                    Log.e("SendGrid", "Failed to send OTP: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("SendGrid", "Error while sending email: ", t);
            }
        });
    }
}
