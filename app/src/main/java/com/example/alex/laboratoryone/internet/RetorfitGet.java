package com.example.alex.laboratoryone.internet;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetorfitGet {
    @GET("latest")
    Call<JsonObject> getSpinerList();

}
