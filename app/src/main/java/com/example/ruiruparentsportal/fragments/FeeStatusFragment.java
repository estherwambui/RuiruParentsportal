package com.example.ruiruparentsportal.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.ruiruparentsportal.model.FeeStatusResponse;
import com.example.ruiruparentsportal.model.FeesStatus;
import com.example.ruiruparentsportal.model.Student;
import com.example.ruiruparentsportal.response.StudentResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.example.ruiruparentsportal.utils.FileUtil;
import com.example.ruiruparentsportal.utils.ScreenshotUtil;
import com.example.ruiruparentsportal.utils.SharedPrefsManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ruiruparentsportal.utils.AppUtils.hideView;
import static com.example.ruiruparentsportal.utils.AppUtils.showView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeeStatusFragment extends Fragment {

    private TextView tvStName, tvStAdm, tvStForm, tvPaid, tvBal, tvTotal;
    private Spinner spAdmNo, spTerm, spForm;
    private int admNo, term, form;
    private ProgressBar spinnerProgressBar, feeStatusProgressBar;
    private ApiService service;
    private ImageButton btnGetFeeStatus;
    private boolean isStatusDisplayed;
    private Bitmap bitmap;
    private View parentViewPDF;
    private List<Student> myStudents = new ArrayList<>();
    private FloatingActionButton downloadFeeStatus;
    private final int PERMISSION_REQUEST_CODE = 324;
    private String str_name, str_adm, str_term;
    private String pngPath; //Path to store screenshot

    public FeeStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fee_status, container, false);

        service = AppUtils.getApiService();

        tvStName = v.findViewById(R.id.tvStName);
        tvStAdm = v.findViewById(R.id.tvStAdm);
        tvTotal = v.findViewById(R.id.tvTotal);
        tvStForm = v.findViewById(R.id.tvStForm);
        tvPaid = v.findViewById(R.id.tvPaid);
        tvBal = v.findViewById(R.id.tvBal);
        spAdmNo = v.findViewById(R.id.spinnerAdmNo);
        spTerm = v.findViewById(R.id.mSpTerm);
        spForm = v.findViewById(R.id.mSpForm);
        btnGetFeeStatus = v.findViewById(R.id.btnGetFeeStatus);
        parentViewPDF = v.findViewById(R.id.parentPDFView);
        feeStatusProgressBar = v.findViewById(R.id.feeStatusProgressBar);
        spinnerProgressBar = v.findViewById(R.id.sProgressBar);
        downloadFeeStatus = v.findViewById(R.id.downloadFeeStatus);

        setUpUI();

        return v;
    }

    private void setUpUI() {
        getMyStudents();
        spAdmNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                admNo = Integer.valueOf(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTerm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                term = Integer.valueOf(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                form = Integer.valueOf(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGetFeeStatus.setOnClickListener(v -> {
            loadFeeStatus();
        });

        downloadFeeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStatusDisplayed) {
                    downloadStatusPdf();
                } else {
                    Toast.makeText(getContext(), "Please load fee status first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void downloadStatusPdf() {
        bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(parentViewPDF); // Take ScreenshotUtil for any view
        saveScreenShot(bitmap);
    }

    private void saveScreenShot(Bitmap bitmap) {
        if (AppUtils.checkPermissionTrue(getContext())) {
            if (bitmap != null) {
                String filename = "Status_" + str_term + "_" + str_name + "_" + str_adm;
                pngPath = Environment.getExternalStorageDirectory() + "/rghs/" + str_name + "_" + str_adm + ".png";
                FileUtil.getInstance().storeBitmap(bitmap, pngPath);
                AppUtils.generatePDF(getContext(), filename, bitmap, parentViewPDF);
            } else {
                Toast.makeText(getContext(), "Failed to download results", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Requesting permissions", Toast.LENGTH_SHORT).show();
            mRequestPermissions();
        }
    }

    private void mRequestPermissions() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                saveScreenShot(bitmap);
            else {
                Toast.makeText(getContext(), "Permission is denied", Toast.LENGTH_SHORT).show();
                mRequestPermissions();
            }
        }
    }

    private void getMyStudents() {
        showView(spinnerProgressBar);
        service.getMyStudents(SharedPrefsManager.getInstance(getContext()).getParentId())
                .enqueue(new Callback<StudentResponse>() {
                    @Override
                    public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                        hideView(spinnerProgressBar);
                        if (response.isSuccessful()) {
                            if (response.body().getError()) {
                                Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    myStudents = response.body().getStudents();
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
            spAdmNo.setAdapter(studentSpinner);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    loadFeeStatus();
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

    private void loadFeeStatus() {
        showView(feeStatusProgressBar);
        service.getFeeStatus(admNo, form, term)
                .enqueue(new Callback<FeeStatusResponse>() {
                    @Override
                    public void onResponse(Call<FeeStatusResponse> call, Response<FeeStatusResponse> response) {
                        hideView(feeStatusProgressBar);
                        if (response.isSuccessful()) {
                            if (response.body().getError()) {
                                showErrorAlertDialog(response.body().getMessage(), false, false);
                            } else {
                                try {
                                    isStatusDisplayed = true;
                                    FeesStatus status = response.body().getFeesStatus();
                                    populateFields(status);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            showErrorAlertDialog("Could not retrieve results at the moment. Please try again later.", true, true);
                        }
                    }

                    @Override
                    public void onFailure(Call<FeeStatusResponse> call, Throwable t) {
                        hideView(feeStatusProgressBar);
                        Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateFields(FeesStatus status) {
        Student t = null;

        for (Student s : myStudents) {
            if (s.getAdm() == admNo) {
                t = s;
                break;
            }
        }

        tvStName.setText(t.getName());
        tvStAdm.setText(String.valueOf(t.getAdm()));
        tvStForm.setText(String.valueOf(t.getForm()));
        tvBal.setText(String.valueOf(status.getBalance()));
        tvPaid.setText(String.valueOf(status.getPaid()));
        tvStForm.setText(String.valueOf(status.getTerm()));
        tvTotal.setText(String.valueOf(12890));
        str_name = t.getName();
        str_adm = String.valueOf(t.getAdm());
        str_term = String.valueOf(status.getTerm());
    }
}
