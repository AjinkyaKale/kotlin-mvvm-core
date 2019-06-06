package com.smartfarming.screenrender.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.smartfarming.screenrender.model.ErrorModel;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Utility {


    /**
     * Method to check whether internet connectivity is present or not
     *
     * @param context
     * @return isConnected
     */
    public static boolean isConnectedToInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();

    }


    /**
     * Method to check if Wi-Fi is available or not
     *
     * @param context
     * @return
     */
    public static boolean isWifiAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected() && activeNetwork.getType() ==
                ConnectivityManager.TYPE_WIFI;

    }


    /**
     * Reads files from raw directory in resources.
     *
     * @param context Context
     * @param rawId   id of a file in raw directory.
     * @return Contents of a given file as a string
     */
    public static String readRawFiles(Context context, int rawId) {
        InputStream is = context.getResources().openRawResource(rawId);

        StringBuilder builder = new StringBuilder();

        try {
            int c;
            while ((c = is.read()) != -1) {
                builder.append((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }


    /**
     * get screen width
     *
     * @param context
     * @return
     */
    public static int getDeviceWidth(Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }


    /**
     * get screen height
     *
     * @param context
     * @return
     */
    public static int getDeviceHeight(Context context) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * post default error model
     */
    public static void postDefaultErrorModel() {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("Something went wrong on server..");
        EventBus.getDefault().post(errorModel);
    }

    public static void postNoInternetErrorModel() {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("network");
        EventBus.getDefault().post(errorModel);
    }

    public static void postStorageSpaceErrorModel() {
        ErrorModel errorModel = new ErrorModel();
        errorModel.setCode("storage");
        EventBus.getDefault().post(errorModel);
    }

    /**
     * Return device's unique identifier
     *
     * @param context activity context
     * @return Android ID unique
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * Get current date in yyyy-mm-datePickerDialog format
     *
     * @return
     */
    public static String getCurrentDate() {
        try {
            Date todayDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
            return formatter.format(todayDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * for request parameter
     * convert string Date To TimeStamp
     *
     * @param strDate
     * @param format
     * @return
     */
    public static long convertDateToTimeStamp(String strDate, String format) {
        try {
            DateFormat formatter = new SimpleDateFormat(format);
            Date date = (Date) formatter.parse(strDate);
            System.out.println("convertDateToTimeStamp " + date.getTime());
            return date.getTime() / 1000;
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * Get current date in yyyy-mm-datePickerDialog format
     *
     * @return
     */
    public static String getCurrentDateAndTime() {
        try {
            Date todayDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            return formatter.format(todayDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns no of days between two dates
     *
     * @param sowingDate
     * @param currentDate
     * @return
     */
    public static int daysBetweenTwoDates(String sowingDate, String currentDate, String dateFormatstr) {
        if (sowingDate != null && currentDate != null) {
            long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatstr, Locale.getDefault());

            try {
                long begin = dateFormat.parse(sowingDate).getTime();
                long end = dateFormat.parse(currentDate).getTime();
                long diff = (end - begin) / (MILLIS_PER_DAY);
                return (int) diff;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    /**
     * Get timestamps from string date
     *
     * @param dateStr
     * @return
     */
    public static long getTimeStampFromDate(String dateStr) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = formatter.parse(dateStr);
            System.out.println("Timestamps from date: " + date.getTime() / 1000);
            return date.getTime() / 1000L;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }

    }


    public static String getDateFromTimeStamp(long timeStamp, String format) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timeStamp * 1000);
            //  calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        } catch (Exception e) {
        }
        return "";
    }


    public static boolean isDataChanged(long lastFetch, long fromServer) {

        // Check  whether timestamp updated at server
        Log.d("", "---------------------------------- ");
        System.out.println("App timestamp (our) >> " + lastFetch);
        System.out.println("Timestamp server >> " + fromServer);

        if (lastFetch == fromServer) {
            return false;
        } else if (lastFetch < fromServer) {
            return true;
        } else {
            return false;
        }

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Hide soft input key board
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Track screen by name
     *
     * @param context
     * @param className
     */
    public static void trackScreenByClassName(Context context, Class className) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "screen");
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, className.getSimpleName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);
    }


    //For future use
    /**
     * Send firebase analytics event
     *
     * @param screenName
     */
//    public static void trackScreenByScreenName(Context context , String screenName) {
//        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(context);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, screenName+"1");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Smartfarming_analytics");
//        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//    }


    /**
     * get added date for the current date
     *
     * @param currentDate
     * @param addNoOfDays
     * @return
     */
    public static String getAddedDate(String currentDate, int addNoOfDays) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(currentDate));
            c.add(Calendar.DATE, addNoOfDays);
            sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
            Date resultDate = new Date(c.getTimeInMillis());
            String dateInString = sdf.format(resultDate);

            String[] dateArr = dateInString.split("-");
            String month = getMonthText(Integer.parseInt(dateArr[1]));
            String mDate = dateArr[0] + "-" + month + "-" + dateArr[2];
            System.out.println("mDate : " + mDate);
            return mDate;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }


    /**
     * get month text using month no
     *
     * @param month
     * @return
     */
    public static String getMonthText(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }


    /**
     * get month no (ex. 3) using month name (ex. March)
     *
     * @param monthName
     * @return
     */
    public static int getMonthNumber(String monthName) {
        try {
            Date date = new SimpleDateFormat("MMMM").parse(monthName);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.MONTH) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * get Date Difference from two given dates
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getDateDifference(String date1, String date2) {

        try {

            SimpleDateFormat dfDate = new SimpleDateFormat(Constants.DATE_FORMAT);
            java.util.Date d = null;
            java.util.Date d1 = null;
            Calendar cal = Calendar.getInstance();
            try {
                d = dfDate.parse(date1);
                d1 = dfDate.parse(date2);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
            System.out.println("diff days :" + diffInDays);

            return diffInDays;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    /**
     * check values is integer or not
     * if not open url(if exists)
     *
     * @param context
     * @param value
     * @return
     */
    public static String isInteger(Context context, String value) {
        if (TextUtils.isEmpty(value)) return null;

        try {
            int intVal = Integer.parseInt(value.trim());
            return String.valueOf(intVal);
        } catch (NumberFormatException e) {
            //Log.d("Clicked Text is URL : ",value);
            e.printStackTrace();
            if (isValidURL(value.trim())) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(value.trim()));
                context.startActivity(i);
            } else {
                AppUtility.showAlertDialog(context, "URL associated with this word is invalid");
                return null;
            }
        }
        return null;
    }


    /**
     * check for valid url
     *
     * @param urlString
     * @return
     */
    public static boolean isValidURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return URLUtil.isValidUrl(urlString) && Patterns.WEB_URL.matcher(urlString).matches();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }


//    /**
//     * alarm for the irrigation fragment.
//     *
//     * @param context
//     */
//    @SuppressLint("NewApi")
//    public static void setAlarm(Context context) {
//
//        Calendar calendar = Calendar.getInstance();
//
//        PendingIntent pendingIntent;
//        Intent myIntent = new Intent(context, WeatherReceiver.class);
//        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        // Set the alarm to start at 9:00 a.m.
//        calendar.set(Calendar.HOUR_OF_DAY, 10); // At the hour you wanna fire
//        calendar.set(Calendar.MINUTE, 0); // Particular minute
//
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);//1000*60*60*24
//    }


    public static String getFormatDate(String date, String currentFormat, String resultFormat) {
        String resultDate;
        SimpleDateFormat dateFormatter = new SimpleDateFormat(currentFormat);
        Date d = null;
        try {
            d = dateFormatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat newDateFormat = new SimpleDateFormat(resultFormat);
        resultDate = newDateFormat.format(d);
        return resultDate;
    }


    public static int getRemainder(int diff, int days) {
        try {
            int result = diff % days;
            if (result < 0)
                result += days;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    public static int getRandomId() {
        int min = 1;
        int max = 1000;
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
