package com.gadget_mart;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gadget_mart.security.SaveSharedPreference;
import com.gadget_mart.ui.order.CustomerOrdersFragment;
import com.gadget_mart.ui.retailer.AllRetailersFragment;
import com.gadget_mart.ui.user.UserProfileFragment;
import com.gadget_mart.util.CommonUtils;
import com.gadget_mart.util.Constant;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.btn_drawer)
    Button drawerButton;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        showInitial(true);
                        break;
                    case R.id.nav_orders:
                        showCustomerOrderHistory(false);
                        break;
                    case R.id.nav_profile:
                        viewProfile(true);
                        break;
                    case R.id.nav_logout:
                        logout();
                        break;
                }

                mDrawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.hideKeyBoard(view, getApplicationContext());
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        if (savedInstanceState == null) {
            showInitial(false);
            return;
        }

    }

    private void showInitial(boolean addToStack) {
        Bundle bundle = new Bundle();
        AllRetailersFragment fragment = new AllRetailersFragment();
        fragment.setShow(true);
        fragment.setArguments(bundle);
        loadFragment(fragment, addToStack, Constant.ALL_RETAILERS_FRAGMENT);
    }

    private void showCustomerOrderHistory(boolean addToStack) {
        Bundle bundle = new Bundle();
        CustomerOrdersFragment customerOrdersFragment = new CustomerOrdersFragment();
        customerOrdersFragment.setArguments(bundle);
        loadFragment(customerOrdersFragment, addToStack, Constant.CUSTOMER_ORDERS_FRAGMENT);
    }

    private void viewProfile(boolean addToStack) {
        Bundle bundle = new Bundle();
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        userProfileFragment.setArguments(bundle);
        loadFragment(userProfileFragment, addToStack, Constant.USER_PROFILE_FRAGMENT);
    }

    public void loadFragment(Fragment fragment, Boolean isAddToStack, String name) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        if (isAddToStack)
            fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void logout() {
        SaveSharedPreference.removeUsername(getApplicationContext());
        SaveSharedPreference.removePassword(getApplicationContext());
        SaveSharedPreference.removeUserId(getApplicationContext());
        Intent intent = new Intent(LandingActivity.this, MainActivity.class);
        startActivity(intent);
    }


}