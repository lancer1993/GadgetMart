package com.gadget_mart.service.softlogic;

import android.content.Context;
import android.util.Log;


import com.gadget_mart.api.softlogic.SoftlogicApiService;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.softlogic.AuthenticatedUserModel;
import com.gadget_mart.security.softlogic.SoftlogicTokenIdentifier;
import com.gadget_mart.security.softlogic.SoftlogicsSaveSharedPreference;
import com.gadget_mart.util.SoftlogicConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoftlogicLoginService {

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
                .baseUrl(SoftlogicConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        SoftlogicApiService apiService = retrofit.create(SoftlogicApiService.class);
        Call<AuthenticatedUserModel> login = apiService.authenticateUser(credentials);
        login.enqueue(new Callback<AuthenticatedUserModel>() {
            @Override
            public void onResponse(Call<AuthenticatedUserModel> call, retrofit2.Response<AuthenticatedUserModel> response) {
                if (response.body() != null) {
                    SoftlogicConstant.isValidLogin = true;
                    SoftlogicsSaveSharedPreference.setUserName(context, credentials.getUsername());
                    SoftlogicsSaveSharedPreference.setPassword(context, credentials.getPassword());
                    SoftlogicsSaveSharedPreference.setUserId(context, response.body().getUser().getIdUser());
                    SoftlogicTokenIdentifier.setTOKEN(response.body().getToken(),context);
                } else {
                    SoftlogicConstant.isValidLogin = false;
                }
            }

            @Override
            public void onFailure(Call<AuthenticatedUserModel> call, Throwable t) {
                SoftlogicConstant.isValidLogin = false;
                Log.e("LOGIN ERROR", t.getMessage());
            }
        });
    }
}
