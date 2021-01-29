package com.gadget_mart.ui.retailer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.adapter.SingerItemStockAdapter;
import com.gadget_mart.callbacks.singer.CategoryCallBack;
import com.gadget_mart.callbacks.singer.ItemStockCallBack;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.singer.Category;
import com.gadget_mart.model.singer.ItemStock;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.security.singer.SingerSaveSharedPreference;
import com.gadget_mart.service.singer.SingerCategoryService;
import com.gadget_mart.service.singer.SingerItemService;
import com.gadget_mart.service.singer.SingerLoginService;
import com.gadget_mart.ui.cart.ShoppingCartFragment;
import com.gadget_mart.util.SingerConstant;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingerItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingerItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progressDialog;

    private int singer;

    Unbinder unbinder;
    @BindView(R.id.singer_category_spinner)
    Spinner singer_category_spinner;
    @BindView(R.id.singer_view_cart_button)
    Button singer_view_cart_button;
    @BindView(R.id.singer_item_recycler)
    RecyclerView singer_item_recycler;
    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();

    public SingerItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingerItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingerItemFragment newInstance(String param1, String param2) {
        SingerItemFragment fragment = new SingerItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            singer = getArguments().getInt("SINGER");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_singer_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        List<ItemStock> list = new ArrayList<>();
        setMainRecycler(getContext(),mLayoutManager1,list);
        Credentials credentials = new Credentials();
        credentials.setUsername(SingerConstant.USERNAME);
        credentials.setPassword(SingerConstant.PASSWORD);

        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            SingerLoginService singerLoginService = new SingerLoginService();
            singerLoginService.checkUserLogin(credentials, getContext());
            if (SingerSaveSharedPreference.getUserId(getContext()) > 0) {
                SingerCategoryService singerCategoryService = new SingerCategoryService();
                singerCategoryService.getCategories(getContext(), new CategoryCallBack() {
                    @Override
                    public void onSuccess(List<Category> list) {
                        progressDialog.dismiss();
                        String[] spinnerArray = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            spinnerMap.put(list.get(i).getIdcategory(), list.get(i).getCategoryName());
                            spinnerArray[i] = list.get(i).getCategoryName();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        singer_category_spinner.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(String message) {
                        progressDialog.dismiss();
                        Snackbar.make(view, "Cannot find categories", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

                SingerItemService singerItemService = new SingerItemService();
                singerItemService.getAllItemStocks(getContext(), new ItemStockCallBack() {
                    @Override
                    public void onSuccess(List<ItemStock> list) {
                        setMainRecycler(getContext(),mLayoutManager1,list);
                    }

                    @Override
                    public void onFailure(String message) {
                        Snackbar.make(view, "Cannot load items", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

            } else {

            }

            singer_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String value = singer_category_spinner.getSelectedItem().toString();
                    int categoryId = getCategoryId(spinnerMap, value);

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    SingerItemService singerItemService = new SingerItemService();
                    singerItemService.getItemsByCategory(getContext(), categoryId, new ItemStockCallBack() {
                        @Override
                        public void onSuccess(List<ItemStock> list) {
                            progressDialog.dismiss();
                            setMainRecycler(getContext(),mLayoutManager1,list);
                        }

                        @Override
                        public void onFailure(String message) {
                            progressDialog.dismiss();
                            Snackbar.make(view, "Cannot load items for " + value, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    @OnClick(R.id.singer_view_cart_button)
    public void goToShoppingCart() {
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE", singer);
        shoppingCartFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, shoppingCartFragment);
        fragmentTransaction.addToBackStack(shoppingCartFragment.getClass().getName());
        fragmentTransaction.commit();
    }

    public int getCategoryId(Map<Integer, String> map, String value) {
        int categoryId = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                categoryId = entry.getKey();
                break;
            }
        }
        return categoryId;
    }

    public void setMainRecycler(Context context, RecyclerView.LayoutManager mLayoutManager, List<ItemStock> list) {
        singer_item_recycler.setLayoutManager(mLayoutManager);
        singer_item_recycler.setItemAnimator(new DefaultItemAnimator());
        singer_item_recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        singer_item_recycler.setAdapter(new SingerItemStockAdapter(context, list));
    }
}