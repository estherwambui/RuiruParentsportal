package com.example.ruiruparentsportal.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.model.Parent;
import com.example.ruiruparentsportal.response.ParentResponse;
import com.example.ruiruparentsportal.response.ResetPassResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.example.ruiruparentsportal.utils.SharedPrefsManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText phone, password;
    Button btnLogin;
    ApiService service;
    ProgressBar loginProgressBar;
    TextView tvForgotPass;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppUtils.setStatusBarGradient(this);

        checkIfIsLoggedIn();

        service = AppUtils.getApiService();

        phone = findViewById(R.id.edtPhoneLogin);
        password = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        tvForgotPass = findViewById(R.id.tvForgotPass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(phone.getText().toString().trim(),
                        password.getText().toString().trim());
            }
        });
        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetPassDialog();
            }
        });


    }

    private void checkIfIsLoggedIn() {
        if (SharedPrefsManager.getInstance(LoginActivity.this).loginCheck()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void loginUser(String phone, final String password) {
        AppUtils.showView(loginProgressBar);
        //Send login request to server - DONE
        //TODO: Remember to use md5 for password
        service.loginParent(AppUtils.LOGIN_TOKEN, phone, password).enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                AppUtils.hideView(loginProgressBar);
                if (response.isSuccessful()) {
                    try {
                        if (response.body().isError()) {
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Parent parent = response.body().getParent();
                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            SharedPrefsManager.getInstance(LoginActivity.this).userLogin(parent.getId(), parent.getName(),
                                    parent.getEmail(), parent.getPhone(), true);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ParentResponse> call, Throwable t) {
                AppUtils.hideView(loginProgressBar);
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    String code = "";
    String newPass = "";
    String new2Pass = "";
    String mphone = "";
    Dialog g;

    private void showResetPassDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_change_pass, null);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        g = new Dialog(this);
        b.setView(v);

        TextInputLayout tilMPhone = v.findViewById(R.id.tilMPhone);
        TextInputEditText edtPhone = v.findViewById(R.id.edtPhoneReset);
        TextInputEditText edtCode = v.findViewById(R.id.edtCode);
        TextInputEditText edtPassword = v.findViewById(R.id.edtPassReset);
        TextInputEditText edtConfPassword = v.findViewById(R.id.edtPass2Reset);
        TextView tvCode = v.findViewById(R.id.tvValidationCode);
        LinearLayout resetPass = v.findViewById(R.id.llResetPass);
        Button btnRequest = v.findViewById(R.id.btnResetPass);

        btnRequest.setOnClickListener(c -> {
            mphone = edtPhone.getText().toString().trim();
            if (phone.equals("")) {
                edtPhone.setError("Phone is required");
                return;
            }
            service.requestCode(mphone).enqueue(new Callback<ResetPassResponse>() {
                @Override
                public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {
                    if (response.isSuccessful()) {
                        if (!response.body().getError()) {
                            resetPass.setVisibility(View.VISIBLE);
                            tvCode.setVisibility(View.VISIBLE);
                            tilMPhone.setVisibility(View.GONE);
                            tvCode.setText("Validation code: " + response.body().getCode());
                            btnRequest.setText("Reset Password");
                            btnRequest.setOnClickListener(v -> {
                                code = edtCode.getText().toString().trim();
                                newPass = edtPassword.getText().toString().trim();
                                new2Pass = edtConfPassword.getText().toString().trim();
                                if (code.equals("") || newPass.equals("") || new2Pass.equals("")) {
                                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                resetThisPassword();
                            });
                        }
                    }
                }

                private void resetThisPassword() {
                    service.resetPassword(mphone, code, newPass).enqueue(new Callback<ResetPassResponse>() {
                        @Override
                        public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {
                            if (response.isSuccessful()) {
                                if (!response.body().getError()) {
                                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    if (g.isShowing()) g.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPassResponse> call, Throwable t) {

                        }
                    });
                }


                @Override
                public void onFailure(Call<ResetPassResponse> call, Throwable t) {

                }
            });
        });
        g = b.create();
        g.show();
    }

}
