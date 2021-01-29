package com.gadget_mart.service.singer;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.singer.SingerApiService;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.singer.AuthenticatedUserModel;
import com.gadget_mart.security.singer.SingerSaveSharedPreference;
import com.gadget_mart.security.singer.SingerTokenIdentifier;
import com.gadget_mart.util.SingerConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingerLoginService {

    public void checkUserLogin(Credentials credentials, Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
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
                .baseUrl(SingerConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SingerApiService apiService = retrofit.create(SingerApiService.class);
        Call<AuthenticatedUserModel> login = apiService.authenticateUser(credentials);
        login.enqueue(new Callback<AuthenticatedUserModel>() {
            @Override
            public void onResponse(Call<AuthenticatedUserModel> call, retrofit2.Response<AuthenticatedUserModel> response) {
                if (response.body() != null) {
                    SingerConstant.isValidLogin = true;
                    SingerSaveSharedPreference.setUserFullName(context, credentials.getUsername());
                    SingerSaveSharedPreference.setPassword(context, credentials.getPassword());
                    SingerSaveSharedPreference.setUserId(context, response.body().getUser().getIduser());
                    SingerTokenIdentifier.setTOKEN(response.body().getToken(), context);
                } else {
                    SingerConstant.isValidLogin = false;
                }
            }

            @Override
            public void onFailure(Call<AuthenticatedUserModel> call, Throwable t) {
                SingerConstant.isValidLogin = false;
                Log.e("LOGIN ERROR", t.getMessage());
            }
        });
    }
}
