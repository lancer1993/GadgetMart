package com.gadget_mart.ui.order;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.adapter.OrderDetailsAdapter;
import com.gadget_mart.callbacks.OrderDetailCallBack;
import com.gadget_mart.model.OrderDetails;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.service.OrderDetailsService;
import com.gadget_mart.ui.retailer.AllRetailersFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderDetailsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.order_details_recycler_view)
    RecyclerView order_details_recycler_view;
    @BindView(R.id.order_detail_home_button)
    Button order_detail_home_button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int orderId;
    private ProgressDialog progressDialog;

    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderDetailsFragment newInstance(String param1, String param2) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
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
            orderId = getArguments().getInt("ORDER_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    CustomerOrdersFragment customerOrdersFragment = new CustomerOrdersFragment();
                    Bundle bundle = new Bundle();
                    customerOrdersFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, customerOrdersFragment);
                    transaction.commit();
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
        List<OrderDetails> list = new ArrayList<>();

        order_details_recycler_view.setLayoutManager(mLayoutManager1);
        order_details_recycler_view.setItemAnimator(new DefaultItemAnimator());
        order_details_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        order_details_recycler_view.setAdapter(new OrderDetailsAdapter(getContext(), list));

        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            OrderDetailsService orderDetailsService = new OrderDetailsService();
            orderDetailsService.getOrderDetailsByOrder(getContext(), orderId, new OrderDetailCallBack() {
                @Override
                public void onSuccess(List<OrderDetails> orderDetailsList) {
                    progressDialog.dismiss();
                    order_details_recycler_view.setLayoutManager(mLayoutManager1);
                    order_details_recycler_view.setItemAnimator(new DefaultItemAnimator());
                    order_details_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                    order_details_recycler_view.setAdapter(new OrderDetailsAdapter(getContext(), orderDetailsList));
                }

                @Override
                public void onFailure(String message) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    @OnClick(R.id.order_detail_home_button)
    public void gotoHome() {
        AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
        Bundle args = new Bundle();
        allRetailersFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, allRetailersFragment);
        fragmentTransaction.commit();
    }
}