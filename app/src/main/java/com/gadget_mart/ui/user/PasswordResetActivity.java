package com.gadget_mart.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gadget_mart.LoginActivity;
import com.gadget_mart.R;
import com.gadget_mart.model.PasswordResetModel;
import com.gadget_mart.service.PasswordResetService;
import com.gadget_mart.util.Constant;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordResetActivity extends AppCompatActivity {

    View view;
    private Context context;

    @BindView(R.id.username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.retype_password_edit_text)
    EditText retypePasswordEditText;
    @BindView(R.id.reset_button)
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_password_reset);
        ButterKnife.bind(this);
        view = this.getWindow().getDecorView().findViewById(R.id.activity_password_reset);

        retypePasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (retypePasswordEditText.getText().toString().length() > 0) {
                    if (passwordEditText.getText().toString().length() == retypePasswordEditText.getText().toString().length()) {
                        if (passwordEditText.getText().toString().equals(retypePasswordEditText.getText().toString())) {

                        } else {
                            Snackbar.make(view, "Password dose not match", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    } else {
                        Snackbar.make(view, "Re-type password is incorrect", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    Snackbar.make(view, "Please enter valid password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    @OnClick(R.id.reset_button)
    public void resetPassword() {
        if (validateForm() == true) {
            PasswordResetModel passwordResetModel = new PasswordResetModel();
            passwordResetModel.setEmail(usernameEditText.getText().toString());
            passwordResetModel.setPassword(passwordEditText.getText().toString());
            PasswordResetService passwordResetService = new PasswordResetService();
            passwordResetService.resetUserPassword(passwordResetModel);
            if(Constant.passwordReset == true){
                Intent intent = new Intent(PasswordResetActivity.this, LoginActivity.class);
                startActivity(intent);
            }else{
                Snackbar.make(view, "Password cannot reset", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Context context = PasswordResetActivity.this;
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean validateForm() {
        boolean validate = true;
        if (usernameEditText.getText().toString() == null && usernameEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter valid email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        if (passwordEditText.getText().toString() == null && passwordEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter a password", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        if (retypePasswordEditText.getText().toString() == null && retypePasswordEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please confirm password", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        return validate;
    }
}