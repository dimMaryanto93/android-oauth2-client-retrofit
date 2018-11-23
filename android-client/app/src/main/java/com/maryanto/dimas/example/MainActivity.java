package com.maryanto.dimas.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.maryanto.dimas.example.api.AuthRepository;
import com.maryanto.dimas.example.service.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doLogin(View view) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "admin");
        params.put("grant_type", "password");

        AuthRepository oauth2 = RestService.oauthService().create(AuthRepository.class);
        String authSecret = "client-android:123456";
        String basicAuthValue = Base64.encodeToString(authSecret.getBytes(), Base64.DEFAULT).replace("\n", "");
        oauth2.login(String.format("Basic %s", basicAuthValue), params)
                .enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                        Log.i("http-request", "onResponse: " + call.request().headers());
                        if (response.code() == 200) {
                            Log.i("oauth2", "onResponse: --> Token" + response.body());
                        } else if (response.code() == 403) {
                            Log.e("oauth2", "onResponse: --> UnAuthorize", null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                        Log.e("response-error", "onFailure: ", t);
                    }
                });

    }
}
