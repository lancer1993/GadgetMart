package com.gadget_mart.ui.cart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.adapter.ShoppingCartAdapter;
import com.gadget_mart.callbacks.ResponseCallBack;
import com.gadget_mart.model.MainOrder;
import com.gadget_mart.model.MainOrderDetails;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.security.SaveSharedPreference;
import com.gadget_mart.service.OrderPlacementService;
import com.gadget_mart.ui.retailer.AbansItemFragment;
import com.gadget_mart.ui.retailer.AllRetailersFragment;
import com.gadget_mart.ui.retailer.SingerItemFragment;
import com.gadget_mart.ui.retailer.SoftlogicFragment;
import com.gadget_mart.util.Constant;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.order_total_text)
    TextView order_total_text;
    @BindView(R.id.shopping_cart_recycler_view)
    RecyclerView shopping_cart_recycler_view;
    @BindView(R.id.place_order_button)
    Button place_order_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progressDialog;

    private int type;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
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
            type = getArguments().getInt("TYPE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        unbinder = ButterKnife.bind(this, view);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (type == 1) {
                        AbansItemFragment abansItemFragment = new AbansItemFragment();
                        Bundle bundle = new Bundle();
                        abansItemFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, abansItemFragment);
                        transaction.addToBackStack(abansItemFragment.getClass().getName());
                        transaction.commit();
                    } else if (type == 2) {
                        SingerItemFragment singerItemFragment = new SingerItemFragment();
                        Bundle bundle = new Bundle();
                        singerItemFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, singerItemFragment);
                        transaction.addToBackStack(singerItemFragment.getClass().getName());
                        transaction.commit();
                    } else if (type == 3) {
                        SoftlogicFragment softlogicFragment = new SoftlogicFragment();
                        Bundle bundle = new Bundle();
                        softlogicFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_frame, softlogicFragment);
                        transaction.addToBackStack(softlogicFragment.getClass().getName());
                        transaction.commit();
                    }
                }
                return false;
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());

        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            shopping_cart_recycler_view.setLayoutManager(mLayoutManager1);
            shopping_cart_recycler_view.setItemAnimator(new DefaultItemAnimator());
            shopping_cart_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            shopping_cart_recycler_view.setAdapter(new ShoppingCartAdapter(getContext(), Constant.mainOrderDetailsList, type));
            calculateTotal(Constant.mainOrderDetailsList);
        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        place_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.mainOrderDetailsList.size() > 0) {
                    MainOrder mainOrder = new MainOrder();
                    mainOrder.setIdReatilers(type);
                    mainOrder.setIdUser(SaveSharedPreference.getUserId(getContext()));
                    mainOrder.setList(Constant.mainOrderDetailsList);
                    Double total = Double.parseDouble(order_total_text.getText().toString());
                    mainOrder.setTotalAmount(total);

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    if (InternetConnectivity.isConnectedToInternet(getContext())) {
                        OrderPlacementService orderPlacementService = new OrderPlacementService();
                        orderPlacementService.saveOrderDetails(getContext(), mainOrder, new ResponseCallBack() {
                            @Override
                            public void onSuccess(String message, int code) {
                                progressDialog.dismiss();
                                Constant.mainOrderDetailsList.clear();
                                AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
                                Bundle bundle = new Bundle();
                                allRetailersFragment.setArguments(bundle);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.content_frame, allRetailersFragment);
                                transaction.addToBackStack(allRetailersFragment.getClass().getName());
                                transaction.commit();
                            }

                            @Override
                            public void onFailure(String message, int code) {
                                progressDialog.dismiss();
                                Snackbar.make(view, message + " Error Code : " + 500, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });
                    } else {
                        Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, "Please add items to the cart", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }

    public void calculateTotal(List<MainOrderDetails> list) {
        double total = 0.0;
        for (MainOrderDetails mainOrderDetails : list) {
            total = total + mainOrderDetails.getItemTotal().doubleValue();
        }
        order_total_text.setText(total + "");
    }
}