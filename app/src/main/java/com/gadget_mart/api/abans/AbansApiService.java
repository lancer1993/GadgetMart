package com.gadget_mart.api.abans;

import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.abans.AuthenticatedUserModel;
import com.gadget_mart.model.abans.Category;
import com.gadget_mart.model.abans.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AbansApiService {

    @POST("authentication/authenticateUser")
    Call<AuthenticatedUserModel> authenticateUser(@Body Credentials credentials);

    @GET("item/getAllItemStocks")
    Call<List<Item>> getAllItemStocks();

    @GET("item/getItemsByCategory/{idCategory}")
    Call<List<Item>> getItemsByCategory(@Path("idCategory") int idCategory);

    @GET("category/getCategories")
    Call<List<Category>> getCategories();

}
