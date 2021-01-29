package com.gadget_mart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gadget_mart.callbacks.AuthenticationCallBack;
import com.gadget_mart.model.Credentials;
import com.gadget_mart.network.InternetConnectivity;
import com.gadget_mart.service.LoginService;
import com.gadget_mart.ui.user.PasswordResetActivity;
import com.gadget_mart.ui.user.SignupActivity;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    private Context context;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        view = this.getWindow().getDecorView().findViewById(R.id.activity_login);
    }

    @OnClick(R.id.login_button)
    void logon() {
        if (InternetConnectivity.isConnectedToInternet(context)) {
            if (usernameEditText.getText().toString().trim().length() == 0) {
                Snackbar.make(view, "Please enter email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (passwordEditText.getText().toString().trim().length() == 0) {
                Snackbar.make(view, "Please enter password", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                String email = String.valueOf(usernameEditText.getText().toString());
                String password = String.valueOf(passwordEditText.getText().toString());
                Credentials credentials = new Credentials();
                credentials.setUsername(email);
                credentials.setPassword(password);
                LoginService loginService = new LoginService();
                loginService.checkUserLogin(credentials, context, new AuthenticationCallBack() {
                    @Override
                    public void onSuccessAuthentication(String message, int code) {
                        Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailureAuthentication(String message, int code) {
                        Snackbar.make(view, "Invalid username or password", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        } else {
            Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.forget_password_text)
    void goToForgotPassword() {
        Context context = LoginActivity.this;
        Intent intent = new Intent(context, PasswordResetActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.account_text)
    void goTpCreateAccount() {
        Context context = LoginActivity.this;
        Intent intent = new Intent(context, SignupActivity.class);
        startActivity(intent);
        finish();
    }

}