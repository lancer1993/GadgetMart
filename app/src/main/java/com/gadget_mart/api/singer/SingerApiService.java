package com.gadget_mart.api.singer;

import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.singer.AuthenticatedUserModel;
import com.gadget_mart.model.singer.Category;
import com.gadget_mart.model.singer.ItemStock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SingerApiService {

    @POST("authentication/authenticateUser")
    Call<AuthenticatedUserModel> authenticateUser(@Body Credentials credentials);

    @GET("itemStock/getAllItemStocks")
    Call<List<ItemStock>> getAllItemStocks();

    @GET("itemStock/getItemsByCategory/{idCategory}")
    Call<List<ItemStock>> getItemsByCategory(@Path("idCategory") int idCategory);

    @GET("category/getCategories")
    Call<List<Category>> getCategories();

}
