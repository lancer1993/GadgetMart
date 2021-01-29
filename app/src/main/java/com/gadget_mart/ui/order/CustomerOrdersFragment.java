package com.gadget_mart.ui.order;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gadget_mart.R;
import com.gadget_mart.adapter.MainOrderAdapter;
import com.gadget_mart.adapter.OrdersAdapter;
import com.gadget_mart.callbacks.OrderCallBack;
import com.gadget_mart.model.Orders;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.security.SaveSharedPreference;
import com.gadget_mart.service.OrderDetailsService;
import com.gadget_mart.ui.retailer.AllRetailersFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerOrdersFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.orders_recycler_view)
    RecyclerView orders_recycler_view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ProgressDialog progressDialog;

    public CustomerOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerOrdersFragment newInstance(String param1, String param2) {
        CustomerOrdersFragment fragment = new CustomerOrdersFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
                    Bundle bundle = new Bundle();
                    allRetailersFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, allRetailersFragment);
                    transaction.addToBackStack(allRetailersFragment.getClass().getName());
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
        List<Orders> list = new ArrayList<>();

        orders_recycler_view.setLayoutManager(mLayoutManager1);
        orders_recycler_view.setItemAnimator(new DefaultItemAnimator());
        orders_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        orders_recycler_view.setAdapter(new MainOrderAdapter(getContext(), list));

        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            int userId = SaveSharedPreference.getUserId(getContext());
            OrderDetailsService orderDetailsService = new OrderDetailsService();
            orderDetailsService.getAllOrdersOfUser(getContext(), userId, new OrderCallBack() {
                @Override
                public void onSuccess(List<Orders> list) {
                    progressDialog.dismiss();
                    orders_recycler_view.setLayoutManager(mLayoutManager1);
                    orders_recycler_view.setItemAnimator(new DefaultItemAnimator());
                    orders_recycler_view.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                    orders_recycler_view.setAdapter(new OrdersAdapter(getContext(), list, new OrdersAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Orders orders) {
                            OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                            Bundle args = new Bundle();
                            args.putInt("ORDER_ID", orders.getIdorder());
                            orderDetailsFragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, orderDetailsFragment);
                            fragmentTransaction.addToBackStack(orderDetailsFragment.getClass().getName());
                            fragmentTransaction.commit();
                        }
                    }));
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
}