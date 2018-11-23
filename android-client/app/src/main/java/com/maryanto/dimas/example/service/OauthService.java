package com.maryanto.dimas.example.service;

import com.maryanto.dimas.example.api.AuthRepository;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OauthService {

    private final static String baseUrl = "http://192.168.1.10:8080";

    private Retrofit oauthApi = null;

    public OauthService() {
        if (oauthApi == null) {
            this.oauthApi = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
    }

    public static AuthRepository oauth2Service() {
        OauthService service = new OauthService();
        return service.oauthApi.create(AuthRepository.class);
    }
}
