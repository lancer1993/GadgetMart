package com.gadget_mart.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.ApiService;
import com.gadget_mart.callbacks.RetailerCallBack;
import com.gadget_mart.model.RetailerModel;
import com.gadget_mart.security.TokenIdentifier;
import com.gadget_mart.util.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetailerService {

    public  RetailerService(){
    }

    public void getAllRetailers(Context context, RetailerCallBack retailerCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + TokenIdentifier.getTOKEN(context))
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
        Call<List<RetailerModel>> call = apiService.getAllRetailers();
        call.enqueue(new Callback<List<RetailerModel>>() {
            @Override
            public void onResponse(Call<List<RetailerModel>> call, retrofit2.Response<List<RetailerModel>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    retailerCallBack.onSuccess(response.body());
                }else{
                    retailerCallBack.onFailure("Retailers cannot found");
                }
            }

            @Override
            public void onFailure(Call<List<RetailerModel>> call, Throwable t) {
                Log.e("Cannot load retailers", t.getMessage());
                retailerCallBack.onFailure("Retailers cannot found");
            }
        });
    }

}
