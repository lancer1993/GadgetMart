package com.gadget_mart.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.ApiService;
import com.gadget_mart.callbacks.AuthenticationCallBack;
import com.gadget_mart.model.AuthenticatedUser;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.security.SaveSharedPreference;
import com.gadget_mart.security.TokenIdentifier;
import com.gadget_mart.util.Constant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {

    public void checkUserLogin(Credentials credentials, Context context, AuthenticationCallBack authenticationCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<AuthenticatedUser> login = apiService.authenticateUser(credentials);
        login.enqueue(new Callback<AuthenticatedUser>() {
            @Override
            public void onResponse(Call<AuthenticatedUser> call, retrofit2.Response<AuthenticatedUser> response) {
                if (response.body() != null) {
                    SaveSharedPreference.setUserName(context, credentials.getUsername());
                    SaveSharedPreference.setPassword(context, credentials.getPassword());
                    SaveSharedPreference.setUserId(context, response.body().getUser().getIduser());
                    TokenIdentifier.setTOKEN(response.body().getToken(),context);
                    authenticationCallBack.onSuccessAuthentication("Successfully Authenticated",200);
                } else {
                    authenticationCallBack.onFailureAuthentication("Authentication Failure",500);
                }
            }

            @Override
            public void onFailure(Call<AuthenticatedUser> call, Throwable t) {
                authenticationCallBack.onFailureAuthentication("Authentication Failure",500);
                Log.e("LOGIN ERROR", t.getMessage());

            }
        });
    }
}
