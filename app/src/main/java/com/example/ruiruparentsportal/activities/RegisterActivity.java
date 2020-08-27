package com.example.ruiruparentsportal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.model.Parent;
import com.example.ruiruparentsportal.response.ParentResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.example.ruiruparentsportal.utils.SharedPrefsManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText phone, password;
    Button btnregister;
    TextView login;
    ApiService service;
    TextInputEditText m_name, m_email, m_phone, m_password;
    ProgressBar registerProgressorbar;
    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AppUtils.setStatusBarGradient(this);

        btnregister = findViewById(R.id.btnRegister);
        m_name = findViewById(R.id.edtName);
        m_email = findViewById(R.id.edtEmail);
        m_phone = findViewById(R.id.edtPhone);
        m_password = findViewById(R.id.edtPassword);
        registerProgressorbar = findViewById(R.id.registerprogressorBar);
        login = findViewById(R.id.tvAlreadyHaveAnAccount);


        service = AppUtils.getApiService();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = m_phone.getText().toString().trim();
                String password = m_password.getText().toString().trim();
                String name = m_name.getText().toString().trim();
                String email = m_email.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    m_name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    m_phone.setError("Phone number is required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    m_email.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    m_password.setError(" password is required");
                    return;
                }
                if (m_password.length() < 4) {
                    m_password.setError("password must me 4 characters");
                    return;
                }
                RegisterUser(name, email, phone, password);
            }
        });
    }

    private void RegisterUser(String name, String email, String phone, final String password) {
        AppUtils.showView(registerProgressorbar);
        //Send login request to server - DONE
        //TODO: Remember to use md5 for password
        /*service.registerParent(AppUtils.REGISTER_TOKEN, name, email, phone, AppUtils.md5(password)).enqueue(new Callback<ParentResponse>() {
            @Override
            public void onResponse(Call<ParentResponse> call, Response<ParentResponse> response) {
                AppUtils.hideView(registerProgressorbar);
                if (response.isSuccessful()) {
                    try {
                        if (response.body().isError()) {
                            Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Parent parent = response.body().getParent();
                            Toast.makeText(RegisterActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            SharedPrefsManager.getInstance(RegisterActivity.this).userLogin(
                                    parent.getId(),
                                    parent.getName(),
                                    parent.getEmail(),
                                    parent.getPhone(),
                                    true);
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error: " + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<ParentResponse> call, Throwable t) {
                AppUtils.hideView(registerProgressorbar);
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}