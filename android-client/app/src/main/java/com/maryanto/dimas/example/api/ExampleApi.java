package com.maryanto.dimas.example.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.Map;

public interface ExampleApi {

    @GET("/api/example/data")
    Call<Map<String, Object>> example(@Header("Authorization") String header);
}
