package com.gadget_mart.ui.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.gadget_mart.R;
import com.gadget_mart.callbacks.UserCallBack;
import com.gadget_mart.model.User;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.service.UserService;
import com.gadget_mart.ui.retailer.AllRetailersFragment;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    Unbinder unbinder;
    private ProgressDialog progressDialog;
    @BindView(R.id.username_text)
    TextView username_text;
    @BindView(R.id.email_text)
    TextView email_text;
    @BindView(R.id.phone_text)
    TextView phone_text;
    @BindView(R.id.nic_text)
    TextView nic_text;
    @BindView(R.id.address_text)
    TextView address_text;
    @BindView(R.id.profile_home_button)
    Button profile_home_button;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (InternetConnectivity.isConnectedToInternet(getContext())) {
            UserService userService = new UserService();
            userService.viewProfile(getContext(), new UserCallBack() {
                @Override
                public void onSuccess(User user) {
                    progressDialog.dismiss();
                    username_text.setText(user.getName());
                    email_text.setText(user.getEmail());
                    phone_text.setText(user.getPhone());
                    nic_text.setText(user.getNic());
                    address_text.setText(user.getAddress());
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

    @OnClick(R.id.profile_home_button)
    public void gotoHome() {
        AllRetailersFragment allRetailersFragment = new AllRetailersFragment();
        Bundle args = new Bundle();
        allRetailersFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, allRetailersFragment);
        fragmentTransaction.commit();
    }
}