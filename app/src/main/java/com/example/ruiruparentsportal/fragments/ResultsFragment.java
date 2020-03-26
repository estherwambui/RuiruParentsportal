package com.example.ruiruparentsportal.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

import static com.example.ruiruparentsportal.utils.AppUtils.hideView;
import static com.example.ruiruparentsportal.utils.AppUtils.showView;
import static com.example.ruiruparentsportal.utils.AppUtils.splitThis;

public class ResultsFragment extends Fragment {
    private Spinner stName, term, form;
    private Integer spTerm, spForm, studentAdmNo;
    private ApiService service;
    private TextView tvPosition, tvMeanGrade, math, eng, kisw, chem, phy, bio, hist, geo, agri, home_scie, business, cre;
    private ImageButton btnGetResults;
    private Button downloadTranscript;
    private ProgressBar resultsProgressBar, spinnerProgressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_results, container, false);

        setUpUserInterface(root);

        return root;
    }

    private void setUpUserInterface(View root) {
        service = AppUtils.getApiService();


        stName = root.findViewById(R.id.spinnerName);
        term = root.findViewById(R.id.spinnerTerm);
        form = root.findViewById(R.id.spinnerForm);
        resultsProgressBar = root.findViewById(R.id.resultsProgressBar);
        spinnerProgressBar = root.findViewById(R.id.spinnerProgressBar);
        tvMeanGrade = root.findViewById(R.id.tvMeanGrade);
        tvPosition = root.findViewById(R.id.tvPosition);
        math = root.findViewById(R.id.tvMaths);
        eng = root.findViewById(R.id.tvEng);
        kisw = root.findViewById(R.id.tvKisw);
        chem = root.findViewById(R.id.tvChem);
        phy = root.findViewById(R.id.tvPhy);
        bio = root.findViewById(R.id.tvBio);
        geo = root.findViewById(R.id.tvGeo);
        hist = root.findViewById(R.id.tvHist);
        cre = root.findViewById(R.id.tvCre);
        agri = root.findViewById(R.id.tvAgri);
        business = root.findViewById(R.id.tvBusiness);
        home_scie = root.findViewById(R.id.tvHomSci);
        btnGetResults = root.findViewById(R.id.btnGetResults);
        downloadTranscript = root.findViewById(R.id.btnDownloadResults);

        downloadTranscript.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Download Complete", Toast.LENGTH_SHORT).show();
            }
        });

        stName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studentAdmNo = Integer.valueOf(parent.getSelectedItem().toString());
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

        //TODO: Get student id and admission number - DONE

        btnGetResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(String.valueOf(studentAdmNo)))
                    Toast.makeText(getContext(), "Student ADM No. is required", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(String.valueOf(spTerm)))
                    Toast.makeText(getContext(), "Term is required", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(String.valueOf(spForm)))
                    Toast.makeText(getContext(), "Form is required", Toast.LENGTH_SHORT).show();
                else
                    retrieveResultsFromServer(studentAdmNo, spTerm, spForm);
            }
        });

        getMyStudents();
    }

    private void getMyStudents() {
        showView(spinnerProgressBar);
        service.getMyStudents(AppUtils.GET_MY_STUDENTS_TOKEN,
                SharedPrefsManager.getInstance(getContext()).getParentId())
                .enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        hideView(spinnerProgressBar);
                        if (response.isSuccessful()) {
                            if (response.body().getError()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                        hideView(spinnerProgressBar);
                        showErrorAlertDialog("No students found under your parentage", false, false);
                    }
                });
    }

    private void populateSpinner(List<Student> myStudents) {
        try {
            ArrayAdapter<Student> studentSpinner = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_spinner_dropdown_item, myStudents);
            studentSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stName.setAdapter(studentSpinner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrieveResultsFromServer(Integer adm_no, Integer term, Integer form) {
        showView(resultsProgressBar);
        service.getResults("results_token", adm_no, form, term).enqueue(new Callback<ResultsResponse>() {
            @Override
            public void onResponse(Call<ResultsResponse> call, Response<ResultsResponse> response) {
                hideView(resultsProgressBar);
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
                    Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    showErrorAlertDialog("Could not retrieve results at the moment. Please try again later.", true, true);
                }
            }

            @Override
            public void onFailure(Call<ResultsResponse> call, Throwable t) {
                hideView(resultsProgressBar);
                showErrorAlertDialog(t.getLocalizedMessage(), false, true);
            }
        });
    }

    private void showErrorAlertDialog(String message, boolean hasRetryAction, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.ic_info);
        builder.setTitle("Information");
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
            String meanGrade = result.getGrade() + " " + result.getMean();
            String studentPosition = result.getPosition() + " of " + result.getStudents();
            tvMeanGrade.setText(meanGrade);
            tvPosition.setText(studentPosition);
            math.setText(!splitThis(result.getMaths())[1].equals("0") ? String.valueOf(result.getMaths()) : returnNA());
            eng.setText(!splitThis(result.getEnglish())[1].equals("0") ? String.valueOf(result.getEnglish()) : returnNA());
            kisw.setText(!splitThis(result.getKiswahili())[1].equals("0") ? String.valueOf(result.getKiswahili()) : returnNA());
            chem.setText(!splitThis(result.getChemistry())[1].equals("0") ? String.valueOf(result.getChemistry()) : returnNA());
            phy.setText(!splitThis(result.getPhysics())[1].equals("0") ? String.valueOf(result.getPhysics()) : returnNA());
            bio.setText(!splitThis(result.getBiology())[1].equals("0") ? String.valueOf(result.getBiology()) : returnNA());
            hist.setText(!splitThis(result.getHistory())[1].equals("0") ? String.valueOf(result.getHistory()) : returnNA());
            geo.setText(!splitThis(result.getGeography())[1].equals("0") ? String.valueOf(result.getGeography()) : returnNA());
            agri.setText(!splitThis(result.getAgriculture())[1].equals("0") ? String.valueOf(result.getAgriculture()) : returnNA());
            business.setText(!splitThis(result.getBusiness())[1].equals("0") ? String.valueOf(result.getBusiness()) : returnNA());
            cre.setText(!splitThis(result.getCre())[1].equals("0") ? String.valueOf(result.getCre()) : returnNA());
            home_scie.setText(!splitThis(result.getHomeScience())[1].equals("0") ? String.valueOf(result.getHomeScience()) : returnNA());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String returnNA() {
        return "-";
    }
}