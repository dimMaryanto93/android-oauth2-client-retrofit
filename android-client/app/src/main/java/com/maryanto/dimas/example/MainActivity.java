package com.maryanto.dimas.example;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.maryanto.dimas.example.api.AuthRepository;
import com.maryanto.dimas.example.api.ExampleApi;
import com.maryanto.dimas.example.service.OauthService;
import com.maryanto.dimas.example.service.RestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String accessToken = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doLogin(View view) throws IOException {
        SharedPreferences storeToken = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = storeToken.edit();

        Map<String, Object> params = new HashMap<>();
        params.put("username", "admin");
        params.put("password", "admin");
        params.put("grant_type", "password");

        String authSecret = "client-android:123456";
        String basicAuthValue = Base64.encodeToString(authSecret.getBytes(), Base64.DEFAULT).replace("\n", "");
        AuthRepository auth = OauthService.oauth2Service();
        auth.login(String.format("Basic %s", basicAuthValue), params)
                .enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                        Log.i("http-request", "onResponse: " + call.request().headers());
                        if (response.code() == 200) {
                            Map<String, Object> body = response.body();
                            Log.i("oauth2", "onResponse: --> Token" + body);
                            accessToken = body.get("access_token").toString();
                            editor.putString(getString(R.string.token), body.get("access_token").toString());
                            editor.apply();
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

    public void getData(View view) {
        ExampleApi service = RestService.exampleService();
        service.example(String.format("Bearer %s", accessToken)).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Log.i("http-api", "onResponse: --> header " + call.request().headers());
                if (response.code() == 200)
                    Log.i("http-api", "onResponse: " + response.body());
                else
                    Log.i("http-api", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.e("http-api", "onFailure: --> error", t);
            }
        });
    }
}
