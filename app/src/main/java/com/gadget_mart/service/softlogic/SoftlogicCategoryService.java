package com.gadget_mart.service.softlogic;

import android.content.Context;
import android.util.Log;


import com.gadget_mart.api.softlogic.SoftlogicApiService;
import com.gadget_mart.callbacks.softlogic.CategoryCallBack;
import com.gadget_mart.model.softlogic.Category;
import com.gadget_mart.security.softlogic.SoftlogicTokenIdentifier;
import com.gadget_mart.util.SoftlogicConstant;

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

public class SoftlogicCategoryService {

    public SoftlogicCategoryService(){

    }

    public void getCategories(Context context, CategoryCallBack categoryCallBack) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + SoftlogicTokenIdentifier.getTOKEN(context))
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

        SoftlogicApiService softlogicApiService = retrofit.create(SoftlogicApiService.class);
        Call<List<Category>> call = softlogicApiService.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, retrofit2.Response<List<Category>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    categoryCallBack.onSuccess(response.body());
                } else {
                    categoryCallBack.onFailure("Categories can not found");
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                categoryCallBack.onFailure("Categories can not found");
                Log.e("CATEGORY", t.getMessage());
            }
        });

    }

}
