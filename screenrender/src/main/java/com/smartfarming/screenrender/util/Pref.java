package com.smartfarming.screenrender.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

/**
 * Created by sopnil on 28/2/18.
 */

public class Pref {

    private static final String PATH = "path";
    private static final String SMART_FARMING_PREF = "smart_farming_prefs";
    private static final String LURE_REPLACEMENT_DATE = "lure_replacement_date";
    private static final String LURE_DATES = "lure_trap_dates";

    public static Pref mInstance;

    Context mContext;
    SharedPreferences sharedPreference;


    public Pref(Context context) {
        mContext = context;
        sharedPreference = mContext.getSharedPreferences(SMART_FARMING_PREF, Context.MODE_PRIVATE);
    }

    public static Pref getInstance(Context context) {
        if (mInstance == null)
            mInstance = new Pref(context);

        return mInstance;
    }

    public String getDefaultPath() {
        return sharedPreference.getString(PATH, null);
    }

    public void setDefaultPath(String path) {
        SharedPreferences.Editor prefsEditor = sharedPreference.edit();
        prefsEditor.putString(PATH, path);
        prefsEditor.commit();
    }

    public void setMajorModerate(String majorModerate) {
        SharedPreferences.Editor prefsEditor = sharedPreference.edit();
        prefsEditor.putString("pest_importance", majorModerate);
        prefsEditor.commit();
    }

    public String getMajorModerate() {
        return sharedPreference.getString("pest_importance", "");
    }

    public void setLureReplacementDate(String date) {
        SharedPreferences.Editor prefsEditor = sharedPreference.edit();
        prefsEditor.putString(LURE_REPLACEMENT_DATE, date);
        prefsEditor.commit();
    }

    public String getLureReplacementDate() {
        return sharedPreference.getString(LURE_REPLACEMENT_DATE, "");
    }

    /**
     * write all the luredate screen with dates
     * if there is no date then  "" empty string
     *
     * @param lureDates
     */
    public void setLureDates(Object lureDates) {
//        for (String screenId : lureDates.keySet()) {
//            mEditor.putString(screenId , lureDates.get(screenId));
//        }
//        mEditor.apply();
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreference.edit();
        String json = gson.toJson(lureDates);
        editor.putString(LURE_DATES, json);
        editor.apply();     // This line is IMPORTANT !!!
    }


    public HashMap<String, String> getLureDates() {
        Gson gson = new Gson();
        String json = sharedPreference.getString(LURE_DATES, "");
        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, String> obj = gson.fromJson(json, type);
        if (obj == null) {
            obj = new HashMap<>();
        }
        return obj;
    }

}
