package com.example.ruiruparentsportal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.model.Result;
import com.example.ruiruparentsportal.response.ResultsResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.example.ruiruparentsportal.utils.SharedPrefsManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private Spinner stName, term, form;
    String spName, spTerm, spForm;
    ApiService service;
    TextView math, eng, kisw, chem, phy, bio, hist, geo, agri, home_scie, business, cre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        service = AppUtils.getApiService();

        stName = findViewById(R.id.spinnerName);
        term = findViewById(R.id.spinnerTerm);
        form = findViewById(R.id.spinnerForm);

        math = findViewById(R.id.tvMaths);
        eng = findViewById(R.id.tvEng);
        kisw = findViewById(R.id.tvKisw);
        chem = findViewById(R.id.tvChem);
        phy = findViewById(R.id.tvPhy);
        bio = findViewById(R.id.tvBio);
        geo = findViewById(R.id.tvGeo);
        hist = findViewById(R.id.tvHist);
        cre = findViewById(R.id.tvCre);
        agri = findViewById(R.id.tvAgri);
        business = findViewById(R.id.tvBusiness);
        home_scie = findViewById(R.id.tvHomSci);

        stName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spName = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spTerm = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        form.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spForm = parent.getSelectedItem().toString();
                tryToRetrieveStudentResults();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void tryToRetrieveStudentResults() {
        service.getResults("results_token", 2,3,1).enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(Call<ResultsResponse> call, Response<ResultsResponse> response) {
                if (response.isSuccessful()){
                    try {
                        Result result = response.body().getResult();
                        populateFields(result);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResultsResponse> call, Throwable t) {

            }
        });
    }

    private void populateFields(Result result) {
        try {
            math.setText(String.valueOf(result.getMaths()));
            eng.setText(String.valueOf(result.getEnglish()));
            kisw.setText(String.valueOf(result.getKiswahili()));
            chem.setText(String.valueOf(result.getChemistry()));
            phy.setText(String.valueOf(result.getPhysics()));
            bio.setText(String.valueOf(result.getBiology()));
            hist.setText(String.valueOf(result.getHistory()));
            geo.setText(String.valueOf(result.getGeography()));
            agri.setText(String.valueOf(result.getAgriculture()));
            business.setText(String.valueOf(result.getBiology()));
            cre.setText(String.valueOf(result.getChemistry()));
            home_scie.setText(String.valueOf(result.getHomeScience()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_account:
                Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                SharedPrefsManager.getInstance(this).logoutUser();
                startActivity(new Intent(ResultsActivity.this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }
}
