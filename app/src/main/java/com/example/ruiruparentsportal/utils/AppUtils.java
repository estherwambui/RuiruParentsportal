package com.example.ruiruparentsportal.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ruiruparentsportal.interfaces.ApiService;
import com.example.ruiruparentsportal.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class AppUtils {
    //Tokens
    public static final String LOGIN_TOKEN = "login_token";
    public static final String REGISTER_TOKEN = "register_token";
    public static final String GET_MY_STUDENTS_TOKEN = "get_my_students_token";

    private static final String TAG = "AppUtils";
    private static final String BASE_URL_OLD = "http://icelabs-eeyan.com/essie-parent-portal/";
    private static final String BASE_URL = "http://icelabs-eeyan.com/parent-portal/";


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
        SimpleDateFormat fromInput = new SimpleDateFormat("yyyy-MM-dd");

        String reformattedStr = "";
        String mDate = "";
        int spacePos = date.indexOf(" ");
        if (spacePos > 0) {
            mDate = date.substring(0, spacePos);
        }

        try {
            Date fromUser = fromInput.parse(mDate);
            reformattedStr = dateFormat.format(fromUser);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return reformattedStr;
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
}
