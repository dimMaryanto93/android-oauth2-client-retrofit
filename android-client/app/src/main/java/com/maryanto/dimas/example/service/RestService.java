package com.maryanto.dimas.example.service;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestService {

    public static Retrofit oauthService() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.10:8080")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
