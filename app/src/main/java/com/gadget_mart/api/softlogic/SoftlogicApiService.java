package com.gadget_mart.api.softlogic;

import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.softlogic.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SoftlogicApiService {

    @POST("authentication/authenticateUser")
    Call<AuthenticatedUserModel> authenticateUser(@Body Credentials credentials);

    @GET("itemStock/getAllItemStocks")
    Call<List<Item>> getAllItemStocks();

    @GET("itemStock/getItemsByCategory/{idCategory}")
    Call<List<Item>> getItemsByCategory(@Path("idCategory") int idCategory);

    @GET("category/getCategories")
    Call<List<Category>> getCategories();
}
