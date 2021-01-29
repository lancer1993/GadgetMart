package com.gadget_mart.service.abans;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.abans.AbansApiService;
import com.gadget_mart.callbacks.abans.ItemCallBack;
import com.gadget_mart.model.abans.Item;
import com.gadget_mart.security.abans.AbansTokenIdentifier;
import com.gadget_mart.util.AbansConstant;

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

public class AbansItemService {

    public AbansItemService() {

    }

    public void getAllItemStocks(Context context, ItemCallBack itemCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + AbansTokenIdentifier.getTOKEN(context))
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

        AbansApiService abansApiService = retrofit.create(AbansApiService.class);
        Call<List<Item>> call = abansApiService.getAllItemStocks();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, retrofit2.Response<List<Item>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    itemCallBack.onSuccess(response.body());
                } else {
                    itemCallBack.onFailure("Items cannot find");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                itemCallBack.onFailure("Items cannot find");
                Log.e("ITEMS", t.getMessage());
            }
        });
    }

    public void getItemsByCategory(Context context, int idCategory, ItemCallBack itemCallBack){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + AbansTokenIdentifier.getTOKEN(context))
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

        AbansApiService abansApiService = retrofit.create(AbansApiService.class);
        Call<List<Item>> call = abansApiService.getItemsByCategory(idCategory);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, retrofit2.Response<List<Item>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    itemCallBack.onSuccess(response.body());
                } else {
                    itemCallBack.onFailure("Items cannot find for category");
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                itemCallBack.onFailure("Items cannot find for category");
                Log.e("ITEMS", t.getMessage());
            }
        });
    }

}
