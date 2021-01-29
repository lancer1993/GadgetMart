package com.gadget_mart.network;

import android.content.Context;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.gadget_mart.api.ApiService;
import com.gadget_mart.model.AuthKeyModel;
import com.gadget_mart.model.TokenModel;
import com.gadget_mart.security.TokenIdentifier;
import com.gadget_mart.util.Constant;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRefreshedToken(final Context context) {
        String token1 = TokenIdentifier.getTOKEN(context);
        JWT jwt = new JWT(token1);
        Date expiresAt = jwt.getExpiresAt();

        JodaTimeAndroid.init(context);

        DateTime exp = new DateTime(expiresAt);
        DateTime now = new DateTime(DateTimeZone.UTC);

        Duration duration = new Duration(now, exp);
        long standardMinutes = duration.getStandardMinutes();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("X-Authorization", "Bearer " + TokenIdentifier.getREFRESHTOKEN())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        if (standardMinutes < 10) {

            AuthKeyModel authKeyModel = new AuthKeyModel();
            authKeyModel.setAuthKey(token1);

            ApiService apiService = retrofit.create(ApiService.class);
            Call<TokenModel> call = apiService.refreshToken(authKeyModel);
            call.enqueue(new Callback<TokenModel>() {
                @Override
                public void onResponse(Call<TokenModel> call, retrofit2.Response<TokenModel> response) {
                    if (response.body() != null) {
                        TokenModel tokenModel = response.body();
                        TokenIdentifier.setTOKEN(tokenModel.getRefreshedToken(),context);
                    }
                }

                @Override
                public void onFailure(Call<TokenModel> call, Throwable t) {
                    Log.e("REFRESH TOKEN ERROR", t.getMessage());
                }
            });
        } else {
            Log.d("REFRESH TOKEN", "LOG OUT");
        }

        return retrofit;
    }
}