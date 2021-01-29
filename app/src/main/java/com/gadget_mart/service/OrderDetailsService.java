package com.gadget_mart.service;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.ApiService;
import com.gadget_mart.callbacks.OrderCallBack;
import com.gadget_mart.callbacks.OrderDetailCallBack;
import com.gadget_mart.model.OrderDetails;
import com.gadget_mart.model.Orders;
import com.gadget_mart.security.TokenIdentifier;
import com.gadget_mart.util.Constant;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailsService {

    public void getAllOrdersOfUser(Context context, int idUser, OrderCallBack orderCallBack) {
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
        Call<List<Orders>> call = apiService.getAllOrdersOfUser(idUser);
        call.enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, retrofit2.Response<List<Orders>> response) {
                if (response.body() != null) {
                    orderCallBack.onSuccess(response.body());
                } else {
                    orderCallBack.onFailure("Cannot find order for users");
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                orderCallBack.onFailure("Cannot find order for users");
                Log.e("ORDERS ", t.getMessage());
            }
        });
    }

    public void getOrderDetailsByOrder(Context context, int idOrder, OrderDetailCallBack orderDetailCallBack) {
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
        Call<List<OrderDetails>> call = apiService.getOrderDetailsByOrder(idOrder);
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, retrofit2.Response<List<OrderDetails>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    orderDetailCallBack.onSuccess(response.body());
                }else{
                    orderDetailCallBack.onFailure("Cannot find order details for the order");
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                orderDetailCallBack.onFailure("Cannot find order details for the order");
                Log.e("ORDER SAVING", t.getMessage());
            }
        });
    }
}
