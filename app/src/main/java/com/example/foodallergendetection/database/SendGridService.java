package com.example.foodallergendetection.database;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendGridService {
    @POST("mail/send")
    Call<Void> sendEmail(@Body SendGridEmailRequest emailRequest);
}
