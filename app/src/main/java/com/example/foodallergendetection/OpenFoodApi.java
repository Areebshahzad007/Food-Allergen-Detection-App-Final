package com.example.foodallergendetection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenFoodApi {

    // Endpoint to get product details based on barcode
    @GET("product/{barcode}.json")
    Call<ProductResponse> getProductByBarcode(@Path("barcode") String barcode);
}
