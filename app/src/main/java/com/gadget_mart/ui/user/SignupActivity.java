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
import com.gadget_mart.model.User;
import com.gadget_mart.service.UserService;
import com.gadget_mart.util.Constant;
import com.gadget_mart.util.ValidationUtil;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    View view;
    private Context context;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.phone_edit_text)
    EditText phoneEditText;
    @BindView(R.id.nic_edit_text)
    EditText nicEditText;
    @BindView(R.id.birthday_edit_text)
    EditText birthdayEditText;
    @BindView(R.id.gender_edit_text)
    EditText genderEditText;
    @BindView(R.id.address_edit_text)
    EditText addressEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditTextEditText;
    @BindView(R.id.retype_password_edit_text)
    EditText retypePasswordEditText;
    @BindView(R.id.submit_button)
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        view = this.getWindow().getDecorView().findViewById(R.id.activity_signup);

        nicEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (nicEditText.getText().toString().length() >= 9 && nicEditText.getText().toString().length() <= 12) {
                    ValidationUtil validationUtil = new ValidationUtil();
                    String gender = validationUtil.gender(nicEditText.getText().toString());
                    genderEditText.setText(gender);
                    genderEditText.setKeyListener(null);
                    String dateOfBirthString = validationUtil.getDateOfBirthString(nicEditText.getText().toString());
                    birthdayEditText.setText(dateOfBirthString);
                    birthdayEditText.setKeyListener(null);
                } else {
                    Snackbar.make(view, "Please enter valid nic number", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (emailEditText.getText().toString().length() > 0) {
                    ValidationUtil validationUtil = new ValidationUtil();
                    validationUtil.validateEmail(emailEditText.getText().toString());
                } else {
                    Snackbar.make(view, "Please enter valid username", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        retypePasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (retypePasswordEditText.getText().toString().length() > 0) {
                    if (passwordEditTextEditText.getText().toString().length() == retypePasswordEditText.getText().toString().length()) {
                        if (passwordEditTextEditText.getText().toString().equals(retypePasswordEditText.getText().toString())) {

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

    @Override
    public void onBackPressed() {
        Context context = SignupActivity.this;
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.submit_button)
    void onSubmitButtonClick() {
        if (validateForm() == true) {
            User user = new User();
            user.setName(nameEditText.getText().toString());
            user.setEmail(emailEditText.getText().toString());
            user.setPassword(passwordEditTextEditText.getText().toString());
            user.setAddress(addressEditText.getText().toString());
            user.setGender(genderEditText.getText().toString());
            user.setPhone(phoneEditText.getText().toString());
            user.setNic(nicEditText.getText().toString());
            user.setEnabled(true);
            user.setSalt(null);
            user.setDateCreated(null);
            user.setLastUpdated(null);

            UserService userService = new UserService();
            userService.submitUserDetails(user);
            if (Constant.isIsUserSubmitted() == true) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Snackbar.make(view, "User creation failed. Please try again!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    public boolean validateForm() {
        boolean validate = true;

        if (nameEditText.getText().toString() == null && nameEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter username", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (emailEditText.getText().toString() == null && emailEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter valid email", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (phoneEditText.getText().toString() != null && !phoneEditText.getText().toString().isEmpty()) {
            if (phoneEditText.getText().toString().length() < 10) {
                validate = false;
                Snackbar.make(view, "Please enter valid mobile number", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }

        if (birthdayEditText.getText().toString() == null && birthdayEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter valid birthday", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

        if (genderEditText.getText().toString() == null && genderEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter valid gender", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

        if (addressEditText.getText().toString() == null && addressEditText.getText().toString().isEmpty()) {
            validate = false;
            Snackbar.make(view, "Please enter address", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        if (passwordEditTextEditText.getText().toString() == null && passwordEditTextEditText.getText().toString().isEmpty()) {
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