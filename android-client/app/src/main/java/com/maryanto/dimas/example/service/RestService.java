package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.api.ExampleApi;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestService {

    private Retrofit baseApi = null;

    public RestService() {

        this.baseApi = new Retrofit.Builder().baseUrl("http://192.168.1.10:8080")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static ExampleApi exampleService() {
        RestService service = new RestService();
        return service.baseApi.create(ExampleApi.class);
    }
}
