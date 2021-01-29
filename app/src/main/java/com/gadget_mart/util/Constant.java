package com.gadget_mart.util;

import com.gadget_mart.model.MainOrderDetails;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String BASE_URL = "http://192.168.43.125:8080/GadgetmartAPI/rest/";
    public static List<MainOrderDetails> mainOrderDetailsList = new ArrayList<>();

    public static boolean isUserSubmitted = false;
    public static boolean passwordReset = false;

    public static boolean isIsUserSubmitted() {
        return isUserSubmitted;
    }

    public static void setIsUserSubmitted(boolean isUserSubmitted) {
        Constant.isUserSubmitted = isUserSubmitted;
    }

    public final static String ALL_RETAILERS_FRAGMENT = "AllRetailersFragment";
    public final static String CUSTOMER_ORDERS_FRAGMENT = "CustomerOrdersFragment";
    public final static String USER_PROFILE_FRAGMENT = "UserProfileFragment";
}
