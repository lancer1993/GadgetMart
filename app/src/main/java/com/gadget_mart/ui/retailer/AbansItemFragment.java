package com.gadget_mart.ui.retailer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.adapter.AbansItemAdapter;
import com.gadget_mart.callbacks.abans.CategoryCallBack;
import com.gadget_mart.callbacks.abans.ItemCallBack;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.model.abans.Category;
import com.gadget_mart.model.abans.Item;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.security.abans.AbansSaveSharedPreference;
import com.gadget_mart.service.abans.AbansCategoryService;
import com.gadget_mart.service.abans.AbansItemService;
import com.gadget_mart.service.abans.AbansLoginService;
import com.gadget_mart.ui.cart.ShoppingCartFragment;
import com.gadget_mart.util.AbansConstant;
import com.gadget_mart.util.Constant;
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
 * Use the {@link AbansItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AbansItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progressDialog;

    private int abans;

    Unbinder unbinder;
    @BindView(R.id.abans_category_spinner)
    Spinner abansCategorySpinner;
    @BindView(R.id.abans_view_cart_button)
    Button abansViewCartButton;
    @BindView(R.id.abans_item_recyler)
    RecyclerView abansItemRecyclerView;
    HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();

    public AbansItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AbansItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AbansItemFragment newInstance(String param1, String param2) {
        AbansItemFragment fragment = new AbansItemFragment();
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
            abans = getArguments().getInt("ABANS");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_abans_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (Constant.mainOrderDetailsList.size() == 0) {
                        AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
                        Bundle bundle = new Bundle();
                        allRetailersFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, allRetailersFragment);
                        transaction.addToBackStack(allRetailersFragment.getClass().getName());
                        transaction.commit();
                    } else {
                        Snackbar.make(view, "Please complete your order or clear the cart", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                return false;
            }
        });
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        List<Item> list = new ArrayList<>();
        setMainRecyclerView(mLayoutManager1, getContext(), list);

        Credentials credentials = new Credentials();
        credentials.setUsername(AbansConstant.USERNAME);
        credentials.setPassword(AbansConstant.PASSWORD);

        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            AbansLoginService abansLoginService = new AbansLoginService();
            abansLoginService.checkUserLogin(credentials, getContext());
            if (AbansSaveSharedPreference.getUserId(getContext()) > 0) {
                AbansCategoryService abansCategoryService = new AbansCategoryService();
                abansCategoryService.getCategories(getContext(), new CategoryCallBack() {
                    @Override
                    public void onSuccess(List<Category> list) {
                        progressDialog.dismiss();
                        String[] spinnerArray = new String[list.size()];
                        for (int i = 0; i < list.size(); i++) {
                            spinnerMap.put(list.get(i).getIdCategory(), list.get(i).getCategoryName());
                            spinnerArray[i] = list.get(i).getCategoryName();
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        abansCategorySpinner.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(String message) {
                        progressDialog.dismiss();
                        Snackbar.make(view, "Cannot find categories", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });                

                AbansItemService abansItemService = new AbansItemService();
                abansItemService.getAllItemStocks(getContext(), new ItemCallBack() {
                    @Override
                    public void onSuccess(List<Item> list) {                        
                        setMainRecyclerView(mLayoutManager1, getContext(), list);
                    }

                    @Override
                    public void onFailure(String message) {
                        Snackbar.make(view, "Cannot load items", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

            } else {
            }
            
            abansCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String value = abansCategorySpinner.getSelectedItem().toString();
                    int categoryId = getCategoryId(spinnerMap, value);
                    AbansItemService abansItemService = new AbansItemService();

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    
                    abansItemService.getItemsByCategory(getContext(), categoryId, new ItemCallBack() {
                        @Override
                        public void onSuccess(List<Item> list) {
                            progressDialog.dismiss();
                            setMainRecyclerView(mLayoutManager1, getContext(), list);
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
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.abans_view_cart_button)
    public void goToShoppingCart() {
        ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE", abans);
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

    public void setMainRecyclerView(RecyclerView.LayoutManager mLayoutManager, Context context, List<Item> list) {
        abansItemRecyclerView.setLayoutManager(mLayoutManager);
        abansItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
        abansItemRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        abansItemRecyclerView.setAdapter(new AbansItemAdapter(context, list));
    }

}