package com.example.ruiruparentsportal.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.model.Result;
import com.example.ruiruparentsportal.model.Student;
import com.example.ruiruparentsportal.response.ResultsResponse;
import com.example.ruiruparentsportal.response.StudentResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.example.ruiruparentsportal.utils.SharedPrefsManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private Spinner stName, term, form;
    private Integer spTerm, spForm, studentAdmNo;
    private ApiService service;
    private TextView math, eng, kisw, chem, phy, bio, hist, geo, agri, home_scie, business, cre;
    private ImageButton btnGetResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        service = AppUtils.getApiService();

        getMyStudents();

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
        btnGetResults = findViewById(R.id.btnGetResults);

        stName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Student selectedStudent = (Student) parent.getSelectedItem();
                studentAdmNo = selectedStudent.getAdmNo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spTerm = Integer.valueOf(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        form.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spForm = Integer.valueOf(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TODO: Get student id and admission number

        btnGetResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(String.valueOf(studentAdmNo)))
                    Toast.makeText(ResultsActivity.this, "Student ADM No. is required", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(String.valueOf(spTerm)))
                    Toast.makeText(ResultsActivity.this, "Term is required", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(String.valueOf(spForm)))
                    Toast.makeText(ResultsActivity.this, "Form is required", Toast.LENGTH_SHORT).show();
                else
                    retrieveResultsFromServer(studentAdmNo, spTerm, spForm);
            }
        });

    }

    private void getMyStudents() {
        service.getMyStudents(AppUtils.GET_MY_STUDENTS_TOKEN,
                SharedPrefsManager.getInstance(this).getParentId())
                .enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getError()) {
                                Toast.makeText(ResultsActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    List<Student> myStudents = response.body().getStudents();
                                    populateSpinner(myStudents);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            showErrorAlertDialog("Something went wrong", false, true);
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentResponse> call, Throwable t) {

                    }
                });
    }

    private void populateSpinner(List<Student> myStudents) {
        try {
            ArrayAdapter<Student> studentSpinner = new ArrayAdapter<>(ResultsActivity.this,
                    android.R.layout.simple_dropdown_item_1line, myStudents);
            studentSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            stName.setAdapter(studentSpinner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveResultsFromServer(Integer adm_no, Integer term, Integer form) {
        service.getResults("results_token", adm_no, form, term).enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(Call<ResultsResponse> call, Response<ResultsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getError()) {
                        showErrorAlertDialog(response.body().getMessage(), false, false);
                    } else {
                        try {
                            Result result = response.body().getResult();
                            populateFields(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    showErrorAlertDialog("Could not retrieve results at the moment. Please try again later.", true, true);
                }
            }

            @Override
            public void onFailure(Call<ResultsResponse> call, Throwable t) {
                showErrorAlertDialog(t.getLocalizedMessage(), false, true);
            }
        });
    }

    private void showErrorAlertDialog(String message, boolean hasRetryAction, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("An error has occurred");
        builder.setMessage(message);

        if (hasRetryAction) {
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    retrieveResultsFromServer(studentAdmNo, spTerm, spForm);
                }
            });
        } else {
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setCancelable(isCancelable);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void populateFields(Result result) {
        try {
            math.setText(result.getMaths() != 0 ? String.valueOf(result.getMaths()) : returnNA());
            eng.setText(result.getEnglish() != 0 ? String.valueOf(result.getEnglish()) : returnNA());
            kisw.setText(result.getKiswahili() != 0 ? String.valueOf(result.getKiswahili()) : returnNA());
            chem.setText(result.getChemistry() != 0 ? String.valueOf(result.getChemistry()) : returnNA());
            phy.setText(result.getPhysics() != 0 ? String.valueOf(result.getPhysics()) : returnNA());
            bio.setText(result.getBiology() != 0 ? String.valueOf(result.getBiology()) : returnNA());
            hist.setText(result.getHistory() != 0 ? String.valueOf(result.getHistory()) : returnNA());
            geo.setText(result.getGeography() != 0 ? String.valueOf(result.getGeography()) : returnNA());
            agri.setText(result.getAgriculture() != 0 ? String.valueOf(result.getAgriculture()) : returnNA());
            business.setText(result.getBusiness() != 0 ? String.valueOf(result.getBusiness()) : returnNA());
            cre.setText(result.getCre() != 0 ? String.valueOf(result.getCre()) : returnNA());
            home_scie.setText(result.getHomeScience() != 0 ? String.valueOf(result.getHomeScience()) : returnNA());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String returnNA() {
        return "N/A";
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
