package com.gadget_mart.service.singer;

import android.content.Context;
import android.util.Log;

import com.gadget_mart.api.singer.SingerApiService;
import com.gadget_mart.callbacks.singer.ItemStockCallBack;
import com.gadget_mart.model.singer.ItemStock;
import com.gadget_mart.security.singer.SingerTokenIdentifier;
import com.gadget_mart.util.SingerConstant;

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

public class SingerItemService {

    public SingerItemService() {

    }

    public void getAllItemStocks(Context context, ItemStockCallBack itemStockCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + SingerTokenIdentifier.getTOKEN(context))
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

        SingerApiService singerApiService = retrofit.create(SingerApiService.class);
        Call<List<ItemStock>> call = singerApiService.getAllItemStocks();
        call.enqueue(new Callback<List<ItemStock>>() {
            @Override
            public void onResponse(Call<List<ItemStock>> call, retrofit2.Response<List<ItemStock>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    itemStockCallBack.onSuccess(response.body());
                } else {
                    itemStockCallBack.onFailure("Cannot find item stock");
                }
            }

            @Override
            public void onFailure(Call<List<ItemStock>> call, Throwable t) {
                itemStockCallBack.onFailure("Cannot find item stock");
                Log.d("ITEM STOCK", t.getMessage());
            }
        });
    }

    public void getItemsByCategory(Context context,  int idCategory, ItemStockCallBack itemStockCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + SingerTokenIdentifier.getTOKEN(context))
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

        SingerApiService singerApiService = retrofit.create(SingerApiService.class);
        Call<List<ItemStock>> call = singerApiService.getItemsByCategory(idCategory);
        call.enqueue(new Callback<List<ItemStock>>() {
            @Override
            public void onResponse(Call<List<ItemStock>> call, retrofit2.Response<List<ItemStock>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    itemStockCallBack.onSuccess(response.body());
                } else {
                    itemStockCallBack.onFailure("Cannot find item stock for category");
                }
            }

            @Override
            public void onFailure(Call<List<ItemStock>> call, Throwable t) {
                itemStockCallBack.onFailure("Cannot find item stock for category");
                Log.d("ITEM STOCK", t.getMessage());
            }
        });
    }
}
