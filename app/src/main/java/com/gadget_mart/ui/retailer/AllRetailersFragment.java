package com.gadget_mart.ui.retailer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.gadget_mart.adapter.MainRetailerAdapter;
import com.gadget_mart.adapter.RetailersAdapter;
import com.gadget_mart.callbacks.RetailerCallBack;
import com.gadget_mart.model.RetailerModel;
import com.gadget_mart.service.RetailerService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AllRetailersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isShow = false;
    private ProgressDialog progressDialog;

    Unbinder unbinder;
    RetailersAdapter retailersAdapter;
    @BindView(R.id.retailer_recycler_view)
    RecyclerView retailerRecyclerView;

    public AllRetailersFragment() {
    }

    public static AllRetailersFragment newInstance(String param1, String param2) {
        AllRetailersFragment fragment = new AllRetailersFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_retailers, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        List<RetailerModel> list = new ArrayList<>();

        retailerRecyclerView.setLayoutManager(mLayoutManager1);
        retailerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        retailerRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        retailerRecyclerView.setAdapter(new MainRetailerAdapter(getContext(),list));

        RetailerService retailerService = new RetailerService();
        retailerService.getAllRetailers(getContext(), new RetailerCallBack() {
            @Override
            public void onSuccess(List<RetailerModel> list) {
                progressDialog.dismiss();
                retailerRecyclerView.setLayoutManager(mLayoutManager1);
                retailerRecyclerView.setItemAnimator(new DefaultItemAnimator());
                retailerRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
                retailerRecyclerView.setAdapter(new RetailersAdapter(getContext(), list, new RetailersAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RetailerModel retailerModel) {
                        if(retailerModel.getIdretailers() == 1){
                            AbansItemFragment abansItemFragment = new AbansItemFragment();
                            Bundle args = new Bundle();
                            args.putInt("ABANS",1);
                            abansItemFragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, abansItemFragment);
                            fragmentTransaction.commit();
                        }else if(retailerModel.getIdretailers() == 2){
                            SingerItemFragment singerItemFragment = new SingerItemFragment();
                            Bundle args = new Bundle();
                            args.putInt("SINGER",2);
                            singerItemFragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, singerItemFragment);
                            fragmentTransaction.commit();
                        }else if(retailerModel.getIdretailers() == 3){
                            SoftlogicFragment softlogicFragment = new SoftlogicFragment();
                            Bundle args = new Bundle();
                            args.putInt("SOFTLOGIC",3);
                            softlogicFragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content_frame, softlogicFragment);
                            fragmentTransaction.commit();
                        }else{

                        }
                    }
                }));
            }

            @Override
            public void onFailure(String message) {
                progressDialog.dismiss();
                Log.e("FAILURE", message);
            }
        });

    }
}