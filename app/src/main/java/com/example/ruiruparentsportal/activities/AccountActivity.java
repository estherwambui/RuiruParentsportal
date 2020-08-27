package com.example.ruiruparentsportal.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.response.ResetPassResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        service = AppUtils.getApiService();

        findViewById(R.id.llChangePass).setOnClickListener(v -> {
            showResetPassDialog();
        });

    }

    String code = "";
    String newPass = "";
    String new2Pass = "";
    String phone = "";

    private void showResetPassDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_change_pass, null);
        AlertDialog.Builder b = new AlertDialog.Builder(this);
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
            phone = edtPhone.getText().toString().trim();
            if (phone.equals("")) {
                edtPhone.setError("Phone is required");
                return;
            }
            service.requestCode(phone).enqueue(new Callback<ResetPassResponse>() {
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
                                    Toast.makeText(AccountActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                resetThisPassword();
                            });
                        }
                    }
                }

                private void resetThisPassword() {
                    service.resetPassword(phone, code, newPass).enqueue(new Callback<ResetPassResponse>() {
                        @Override
                        public void onResponse(Call<ResetPassResponse> call, Response<ResetPassResponse> response) {
                            if (response.isSuccessful()) {
                                if (!response.body().getError()) {
                                    Toast.makeText(AccountActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    onBackPressed();
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

        b.show();
    }
}
