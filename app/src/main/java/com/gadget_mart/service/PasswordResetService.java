package com.gadget_mart.service;

import android.util.Log;

import com.gadget_mart.api.ApiService;
import com.gadget_mart.model.PasswordResetModel;
import com.gadget_mart.model.ResponseModel;
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

public class PasswordResetService {

    public void resetUserPassword(PasswordResetModel passwordResetModel) {
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
        Call<ResponseModel> call = apiService.resetPassword(passwordResetModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                if (response.body() != null) {
                    ResponseModel responseModel = response.body();
                    if (responseModel.getCode() == 200) {
                        Constant.passwordReset = true;
                    } else {
                        Constant.passwordReset = false;
                    }
                } else {
                    Constant.passwordReset = false;
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Constant.passwordReset = false;
                Log.e("Cannot reset password", t.getMessage());
            }
        });
    }
}
