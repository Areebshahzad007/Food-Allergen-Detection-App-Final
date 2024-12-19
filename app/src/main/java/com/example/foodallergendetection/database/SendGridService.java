package com.example.foodallergendetection.database;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SendGridService {

    String apiKey = "mlsn.874ab0550fc36475eb4b5b7648995609be6daf1dd7901636d45c9456a64564c5";

    @Headers({
            "Authorization: Bearer "+apiKey,
            "Content-Type: application/json"
    })
    @POST("mail/send")
    Call<Void> sendEmail(@Body SendGridEmailRequest emailRequest);
}
