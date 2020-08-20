package com.example.ruiruparentsportal.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.ruiruparentsportal.R;
import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.model.PDFDownload;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class AppUtils {
    //Tokens
    public static final String LOGIN_TOKEN = "login_token";
    public static final String REGISTER_TOKEN = "register_token";
    public static final String GET_MY_STUDENTS_TOKEN = "get_my_students_token";
    public static final String GET_NEWS_TOKEN = "get_news_token";
    public static final String GET_FEE_STATUS_TOKEN = "fees_status_token";
    public static final String GET_FEE_STRUCTURE_TOKEN = "fees_structure_token";

    private static final String TAG = "AppUtils";
    private static final String BASE_URL_OLD = "http://icelabs-eeyan.com/essie-parent-portal/";
    private static final String BASE_URL = "http://192.168.20.31:8000/api/";
    private static String pngPath, pdfPath;
    private static Context context;


    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

   /* public static void displayToast(Activity activity, boolean isSuccess, String message) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout;
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        if (isSuccess) {
            layout = inflater.inflate(R.layout.success_toast, null);
        } else {
            layout = inflater.inflate(R.layout.failure_toast, null);
        }
        toast.setView(layout);
        TextView tv_message = layout.findViewById(R.id.tv_message);
        tv_message.setText(message);
        toast.show();
    }*/

    public static String getGrade(int score) {
        if (score >= 80) {
            return score + " A";
        } else if (score >= 75) {
            return score + " A-";
        } else if (score >= 70) {
            return score + " B+";
        } else if (score >= 65) {
            return score + " B";
        } else if (score >= 60) {
            return score + " B-";
        } else if (score >= 55) {
            return score + " C+";
        } else if (score >= 50) {
            return score + " C";
        } else if (score >= 45) {
            return score + " C-";
        } else if (score >= 40) {
            return score + " D+";
        } else if (score >= 35) {
            return score + " D";
        } else if (score >= 30) {
            return score + " D-";
        } else {
            return score + " E";
        }
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes("UTF-8"));
            BigInteger hash = new BigInteger(1, md.digest());
            String result = hash.toString(16);
            if ((result.length() % 2) != 0) {
                result = "0" + result;
            }
            /*MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) hexString.append(Integer.toHexString(0xFF & b));
            //Log.d(TAG, "md5: " + hexString.toString());*/
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        SimpleDateFormat fromInput = new SimpleDateFormat("yyyy-MM-dd");//2020-11-17

        String reformattedStr = "";
        String mDate = "";
        int spacePos = date.indexOf(" ");
        if (spacePos > 0) {
            mDate = date.substring(0, spacePos);
        }

        try {
            Date fromUser = fromInput.parse(mDate);
            reformattedStr = dateFormat.format(fromUser);
            return reformattedStr;
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }


    public static void showView(View v) {
        if (v.getVisibility() == GONE || v.getVisibility() == INVISIBLE) {
            v.setVisibility(VISIBLE);
            v.animate()
                    .alpha(1.0f)
                    .setDuration(500);
        }
    }

    public static void hideView(View v) {
        if (v.getVisibility() == VISIBLE) {
            v.animate()
                    .alpha(0.0f)
                    .setDuration(500);
            v.setVisibility(GONE);
        }
    }

    public static String[] splitThis(String raw) {
        return raw.split(" ");
    }

    public static String FormatCurrency(int raw) {
        String raw_str = String.valueOf(raw);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setCurrency(Currency.getInstance("KES"));
        numberFormat.setMinimumFractionDigits(2);
        String currency = null;
        try {
            currency = String.valueOf(numberFormat.format(numberFormat.parse(raw_str)));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Ksh. " + currency;
    }

    /* Make plural method
     * Takes a string and adds s to make it plural
     * @params String
     * @return plural if string didn't originally have s else returns the raw string
     * @throws IndexOutOfBoundsException
     * */
    public static String makePlural(String s) {
        int l = s.length();
        String lastChar;
        try {
            lastChar = String.valueOf(s.charAt(l - 1));
            if (!lastChar.equals("s")) {
                if (lastChar.equals("y"))
                    return s.replace("y", "ies");
                return s + "s";
            } else {
                return s;
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d(TAG, "getLastCharacter: index out of bounds! Exiting...");
            return s;
        }
    }

    public static void setForegroundColor(Context c, ImageView i, TextView t) {
        try {
            t.setTextColor(c.getResources().getColor(R.color.colorPrimary));
            i.setColorFilter(ContextCompat.getColor(c, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disableButton(Button btn, boolean removeText) {
        if (removeText)
            btn.setText("");
        btn.setEnabled(false);
    }

    public static void enableButton(Button btn, String btnText) {
        btn.setText(btnText);
        btn.setEnabled(true);
    }

    public static void enableButton(Button btn) {
        btn.setEnabled(true);
    }

    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.layout_background);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    public static boolean checkPermissionTrue(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static void generatePDF(Context context, String filename, Bitmap bitmap, View snackBarView) {
        AppUtils.context = context;
        pdfPath = context.getExternalFilesDir("rghs/").getAbsolutePath();
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        document.finishPage(page);
        File filePath = new File(pdfPath);
        File pdfFile = new File(filePath, filename + ".pdf");

        if (!filePath.exists()) {
            filePath.mkdirs();
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            document.writeTo(new FileOutputStream(pdfFile));
            deleteScreenshot(filename);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
        Snackbar sn = Snackbar.make(snackBarView, "Download complete", Snackbar.LENGTH_INDEFINITE);
        sn.setAction("Open", v -> {
            Toast.makeText(context, "Opening document...", Toast.LENGTH_SHORT).show();
            openPdf(context, pdfFile);
        });
        sn.show();
    }

    public static void deleteScreenshot(String filename) {
        pngPath = Environment.getExternalStorageDirectory() + "/rghs/" + filename + ".png";
        File fileToBeDeleted = new File(pngPath);
        try {
            if (fileToBeDeleted.delete()) {
                Log.i(TAG, "deleteScreenshot: Screenshot deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openPdf(Context context, File pdfFile) {
        if (pdfFile.exists()) //Checking if the file exists or not
        {
            Uri mPath = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pdfFile);
            // Uri mPath = FileProvider.getUriForFile(getContext(), null, pdfFile);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(mPath, "application/pdf");
            objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            objIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(objIntent);//Starting the pdf viewer
        } else {
            Toast.makeText(context, "The file not exists! ", Toast.LENGTH_SHORT).show();
        }
    }

    public static List<PDFDownload> getDownloadedFiles(Context context) {
        String downloadsPath = context.getExternalFilesDir("rghs/").getAbsolutePath();
        List<PDFDownload> pdfDownloads = new ArrayList<>();
        File[] files = new File(downloadsPath).listFiles();

        for (File f : files) {
            PDFDownload pdfDownload = new PDFDownload(f.getName());
            pdfDownloads.add(pdfDownload);
        }

        return pdfDownloads;
    }
}
