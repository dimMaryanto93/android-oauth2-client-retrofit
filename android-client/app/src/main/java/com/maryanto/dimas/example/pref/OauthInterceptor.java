package com.maryanto.dimas.example.pref;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OauthInterceptor implements Interceptor {

    private final String accessToken;

    public OauthInterceptor(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest =
                request.newBuilder()
                        .addHeader(
                                "Authorization",
                                String.format("Bearer %s", accessToken))
                        .build();
        return chain.proceed(newRequest);
    }
}
