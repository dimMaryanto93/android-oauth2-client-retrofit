package com.maryanto.dimas.example.api;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface AuthRepository {

    @POST("/oauth/token")
    Call<Map<String, Object>> login(@Header("Authorization") String auth, @QueryMap Map<String, Object> params);
}
