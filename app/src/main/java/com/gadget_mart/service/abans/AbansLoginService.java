package com.gadget_mart.service.abans;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.abans.AbansApiService;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.abans.AuthenticatedUserModel;
import com.gadget_mart.security.abans.AbansSaveSharedPreference;
import com.gadget_mart.security.abans.AbansTokenIdentifier;
import com.gadget_mart.util.AbansConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AbansLoginService {

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
                .baseUrl(AbansConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        AbansApiService apiService = retrofit.create(AbansApiService.class);
        Call<AuthenticatedUserModel> call = apiService.authenticateUser(credentials);
        call.enqueue(new Callback<AuthenticatedUserModel>() {
            @Override
            public void onResponse(Call<AuthenticatedUserModel> call, retrofit2.Response<AuthenticatedUserModel> response) {
                if (response.body() != null) {
                    AbansConstant.isValidLogin = true;
                    AbansSaveSharedPreference.setUserName(context,credentials.getUsername());
                    AbansSaveSharedPreference.setPassword(context, credentials.getPassword());
                    AbansSaveSharedPreference.setUserId(context, response.body().getUser().getIdUser());
                    AbansTokenIdentifier.setTOKEN(response.body().getToken(), context);
                } else {
                    AbansConstant.isValidLogin = false;
                }
            }

            @Override
            public void onFailure(Call<AuthenticatedUserModel> call, Throwable t) {
                AbansConstant.isValidLogin = false;
                Log.e("LOGIN ERROR", t.getMessage());
            }
        });
    }
}
