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
import com.example.ruiruparentsportal.model.FeeStructure;
import com.example.ruiruparentsportal.response.FeeStructureResponse;
import com.example.ruiruparentsportal.utils.AppUtils;
import com.example.ruiruparentsportal.utils.FileUtil;
import com.example.ruiruparentsportal.utils.ScreenshotUtil;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeeStructureFragment extends Fragment {

    private ApiService service;
    private TextView tvTuition, tvRegistration, tvLibrary, tvActivity, tvExams, tvDevt, tvMentorship,
            tvCautionMoney, tvStudID, tvClubs, tvBoarding, tvTotalFee, tv_m_term, tv_m_form;
    private String Tuition, Registration, Library, Activity, Exams, Devt, Membership,
            CautionMoney, StudID, Clubs, Boarding, TotalFee, Term, Form;
    private Spinner spnTermStruct, spnFormStruct;
    private ImageButton btnGetStructure;
    private Button btnDownloadStructure;
    private Integer term;
    private Integer form;
    private FeeStructure feeStructure;

    private Bitmap bitmap;
    private View parentViewPDF;
    private final int PERMISSION_REQUEST_CODE = 332;
    private String pngPath, pdfPath; //Path to store screenshot

    private ProgressBar structureProgressBar;

    private boolean areFeesDisplayed;

    public FeeStructureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_fee_structure, container, false);
        service = AppUtils.getApiService();

        setUpUserInterface(root);

        return root;
    }

    private void setUpUserInterface(View root) {
        parentViewPDF = root.findViewById(R.id.cvFeeStructure);
        structureProgressBar = root.findViewById(R.id.structureProgressBar);

        tv_m_term = root.findViewById(R.id.tv_m_term);
        tv_m_form = root.findViewById(R.id.tv_m_form);

        tvTuition = root.findViewById(R.id.tvTuition);
        tvRegistration = root.findViewById(R.id.tvRegistration);
        tvLibrary = root.findViewById(R.id.tvLibrary);
        tvActivity = root.findViewById(R.id.tvActivity);
        tvExams = root.findViewById(R.id.tvExams);
        tvDevt = root.findViewById(R.id.tvDevt);
        tvMentorship = root.findViewById(R.id.tvMentorship);
        tvCautionMoney = root.findViewById(R.id.tvCautionMoney);
        tvStudID = root.findViewById(R.id.tvStudID);
        tvClubs = root.findViewById(R.id.tvClubs);
        tvBoarding = root.findViewById(R.id.tvBoarding);
        tvTotalFee = root.findViewById(R.id.tvTotalFee);

        spnTermStruct = root.findViewById(R.id.spnTermStruct);
        spnFormStruct = root.findViewById(R.id.spnFormStruct);

        btnGetStructure = root.findViewById(R.id.btnGetStructure);
        btnDownloadStructure = root.findViewById(R.id.btnDownloadStructure);

        btnGetStructure.setOnClickListener(v -> {
            term = Integer.valueOf(spnTermStruct.getSelectedItem().toString());
            form = Integer.valueOf(spnFormStruct.getSelectedItem().toString());
            getFeeStructure(form, term);
        });

        btnDownloadStructure.setOnClickListener(v -> {
            if (areFeesDisplayed) {
                pretendToDownloadPDF();
            } else {
                Toast.makeText(getContext(), "Please load fee structure first", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getFeeStructure(Integer form, Integer term) {
        AppUtils.showView(structureProgressBar);
        areFeesDisplayed = false;
        service.getFeeStructure(form, term)
                .enqueue(new Callback<FeeStructureResponse>() {
                    @Override
                    public void onResponse(Call<FeeStructureResponse> call, Response<FeeStructureResponse> response) {
                        AppUtils.hideView(structureProgressBar);
                        if (response.isSuccessful()) {
                            if (!response.body().getError()) {
                                feeStructure = response.body().getFeeStructure();
                                populateFields();
                            } else {
                                showErrorAlertDialog(response.body().getMessage(), true, true);
                            }
                        } else {
                            showErrorAlertDialog("Could not fees structure at the moment. Please try again later.", true, true);
                        }
                    }

                    @Override
                    public void onFailure(Call<FeeStructureResponse> call, Throwable t) {
                        Toast.makeText(getContext(), "An error has occurred", Toast.LENGTH_SHORT).show();
                        AppUtils.hideView(structureProgressBar);
                    }
                });
    }

    private void populateFields() {
        /*tvTuition, tvRegistration, tvLibrary, tvActivity, tvExams, tvDevt, tvMentorship,
            tvCautionMoney, tvStudID, tvClubs, tvBoarding, tvTotalFee, tv_m_term, tv_m_form*/
        areFeesDisplayed = true;
        tvTuition.setText(stringify(feeStructure.getTuition()));
        tvRegistration.setText(stringify(feeStructure.getRegistration()));
        tvLibrary.setText(stringify(feeStructure.getLibrary()));
        tvActivity.setText(stringify(feeStructure.getActivity()));
        tvExams.setText(stringify(feeStructure.getExams()));
        tvDevt.setText(stringify(feeStructure.getDevelopment()));
        //tvMentorship.setText(stringify(feeStructure.get()));
        tvCautionMoney.setText(stringify(feeStructure.getCaution()));
        tvStudID.setText(stringify(feeStructure.getStudentId()));
        tvClubs.setText(stringify(feeStructure.getClubsAndSocieties()));
        tvBoarding.setText(stringify(feeStructure.getBoarding()));
        tvTotalFee.setText(stringify(feeStructure.getTotal()));
        tv_m_term.setText("Term - " + feeStructure.getTerm());
        tv_m_form.setText("Form - " + feeStructure.getForm());
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
                    getFeeStructure(form, term);
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

    private String stringify(int raw) {
        return String.format(Locale.US, "%,d", raw);
    }

    private void pretendToDownloadPDF() {
        bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(parentViewPDF); // Take ScreenshotUtil for any view
        saveScreenShot(bitmap);
    }

    private void saveScreenShot(Bitmap bitmap) {
        if (AppUtils.checkPermissionTrue(getContext())) {
            if (bitmap != null) {
                String filename = "Fee_Structure_F" + form + "_T" + term;
                pngPath = Environment.getExternalStorageDirectory() + "/rghs/" + "Fee_Structure_F" + form + "_T" + term + ".png ";
                FileUtil.getInstance().storeBitmap(bitmap, pngPath);
                AppUtils.generatePDF(getContext(), filename, bitmap, parentViewPDF);
            } else {
                Toast.makeText(getContext(), "Failed to download fees structure", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Requesting permissions", Toast.LENGTH_SHORT).show();
            mRequestPermissions();
        }
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

    private void mRequestPermissions() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }
}
