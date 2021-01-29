package com.gadget_mart.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.ApiService;
import com.gadget_mart.callbacks.ResponseCallBack;
import com.gadget_mart.model.MainOrder;
import com.gadget_mart.model.ResponseModel;
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

public class OrderPlacementService {

    public void saveOrderDetails(Context context, MainOrder mainOrder, ResponseCallBack responseCallBack) {
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
        Call<ResponseModel> call = apiService.saveOrderDetails(mainOrder);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, retrofit2.Response<ResponseModel> response) {
                Log.d("RESPONSE BODY",response.body().toString());
                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        responseCallBack.onSuccess(response.body().getResponse(), response.body().getCode());
                    }else{
                        responseCallBack.onFailure(response.body().getResponse(), response.body().getCode());
                    }
                } else {
                    responseCallBack.onFailure("Cannot save the order", 500);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("ORDER SAVING", t.getMessage());
                responseCallBack.onFailure("Cannot save the order", 500);
            }
        });
    }
}
