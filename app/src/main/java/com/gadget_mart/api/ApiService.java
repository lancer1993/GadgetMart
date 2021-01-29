package com.gadget_mart.api;

import com.gadget_mart.model.AuthKeyModel;
import com.gadget_mart.model.AuthenticatedUser;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.MainOrder;
import com.gadget_mart.model.OrderDetails;
import com.gadget_mart.model.Orders;
import com.gadget_mart.model.PasswordResetModel;
import com.gadget_mart.model.ResponseModel;
import com.gadget_mart.model.RetailerModel;
import com.gadget_mart.model.TokenModel;
import com.gadget_mart.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("authentication/authenticateUser")
    Call<AuthenticatedUser> authenticateUser(@Body Credentials credentials);

    @POST("authentication/refreshToken")
    Call<TokenModel> refreshToken(@Body AuthKeyModel authKeyModel);

    @POST("user/saveUser")
    Call<ResponseModel> saveUser(@Body User user);

    @POST("user/resetPassword")
    Call<ResponseModel> resetPassword(@Body PasswordResetModel passwordResetModel);

    @GET("retailer/getAllRetailers")
    Call<List<RetailerModel>> getAllRetailers();

    @POST("orderDetails/saveOrderDetails")
    Call<ResponseModel> saveOrderDetails(@Body MainOrder mainOrder);

    @GET("order/getAllOrdersOfUser/{idUser}")
    Call<List<Orders>> getAllOrdersOfUser(@Path("idUser") int idUser);

    @GET("orderDetails/getOrderDetailsByOrder/{idOrder}")
    Call<List<OrderDetails>> getOrderDetailsByOrder(@Path("idOrder") int idOrder);

    @GET("user/getUserById/{id}")
    Call<User> getUserById(@Path("id") int id);

}
