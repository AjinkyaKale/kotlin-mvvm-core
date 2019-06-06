package com.smartfarming.screenrender.manager;

import android.content.Context;
import android.text.TextUtils;
import com.smartfarming.screenrender.util.Constants;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by samsung on 17/5/17.
 */

public class LanguageManager {


    //private static final LanguageManager ourInstance = new LanguageManager();

    private static LanguageManager langManager;

    private static JSONObject languageObject;
    Context mContext;


    public static LanguageManager getInstance(Context context) {
        if (langManager == null) {
            langManager = new LanguageManager(context);
            //createLanguageObjectFromAssets(context);
            createObjectFromJson(context);

        }
        return langManager;
    }

    /**
     * Clear previous instance
     */
    public static void clearInstance() {
        langManager = null;
        languageObject = null;
    }

    private LanguageManager(Context context) {
        this.mContext = context;
    }


    public static void createObjectFromJson(Context context) {

        try {
            String labelResponse = FileManager.readLabel(context);
            if (!TextUtils.isEmpty(labelResponse)) {
                languageObject = new JSONObject(labelResponse);
                /*
                JSONObject data = jsonObject.getJSONObject("data");
                languageObject = data.getJSONObject("language_details");*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create language json object to set the label to the views.
     *
     * @param context
     */

    public static void createLanguageObjectFromAssets(Context context) {

        if (languageObject != null) {
            return;
        }

        try {
            InputStream is = context.getAssets().open("label.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            languageObject = new JSONObject(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * get label for key from label.json
     *
     * @param key
     * @return
     */
    public String getLabel(String key) {
        try {

            String label = languageObject.getString(key);
            if (!TextUtils.isEmpty(label) && !label.equalsIgnoreCase("null")) {
                return label;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * get label array  for keys  array for multiple keys from label.json
     *
     * @param keys
     * @return
     */
    public String[] getArrayOfLabels(String[] keys) {

        String[] result = new String[keys.length];

        for (int i = 0; i < keys.length; i++) {
            result[i] = getLabel(keys[i]);
        }

        return result;
    }


    /*  */

    /**
     * get label array for key from label.json
     *
     * @param key
     * @return
     *//*
    public String[] getLabelArray(String key) {
        String[] list = null;

        try{
            JSONArray jsonArray = languageObject.getJSONArray(key);

            list = new String[jsonArray.length()+1];

            list[0] = Constants.PLEASE_SELECT;
            for (int i = 0; i <jsonArray.length() ; i++) {
                list[i+1]=jsonArray.getString(i);
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }*/
    public String[] getLabelArrayNew(String key) {
        String[] list = null;
        try {
            switch (key) {

                case Constants.SPIN_CERTIFICATE:
                    list = new String[4];
                    list[0] = getLabel("PLEASE_SELECT");
                    list[1] = getLabel("profile_certificate_1");
                    list[2] = getLabel("profile_certificate_2");
                    list[3] = getLabel("profile_certificate_3");
                    break;

                case Constants.SPIN_IRRIGATION_SYS:
                    list = new String[5];
                    list[0] = getLabel("Please_select");
                    list[1] = getLabel("irrigation_system_1");
                    list[2] = getLabel("irrigation_system_2");
                    list[3] = getLabel("irrigation_system_3");
                    list[4] = getLabel("irrigation_system_4");
                    break;

                case Constants.SPIN_SOIL_TYPE:
                    list = new String[4];
                    list[0] = getLabel("Please_select");
                    list[1] = getLabel("profile_soil_type_1");
                    list[2] = getLabel("profile_soil_type_2");
                    list[3] = getLabel("profile_soil_type_3");
                    break;

                case Constants.SPIN_SEED_TYPE:
                    list = new String[2];
                    list[0] = getLabel("Please_select");
                    list[1] = getLabel("profile_seed_type_1");
                    break;

                case Constants.SPIN_FARMING_TYPE:
                    list = new String[3];
                    list[0] = getLabel("Please_select");
                    list[1] = getLabel("profile_farming_type_1");
                    list[2] = getLabel("profile_farming_type_2");
                    break;

                case Constants.SPIN_WEATHER:
                    list = new String[5];
                    list[0] = getLabel("PLEASE_SELECT");
                    list[1] = getLabel("WEATHER_OPTION_TEXT_1");
                    list[2] = getLabel("WEATHER_OPTION_TEXT_2");
                    list[3] = getLabel("WEATHER_OPTION_TEXT_3");
                    list[4] = getLabel("WEATHER_OPTION_TEXT_4");
                    break;

                case Constants.SPIN_RAIN_EVENT:
                    list = new String[4];
                    list[0] = getLabel("PLEASE_SELECT");
                    list[1] = getLabel("WEATHER_RAIN_EVENT_1");
                    list[2] = getLabel("WEATHER_RAIN_EVENT_2");
                    list[3] = getLabel("WEATHER_RAIN_EVENT_3");
                    break;

                case Constants.SPIN_RUNOFF:
                    list = new String[3];
                    list[0] = getLabel("PLEASE_SELECT");
                    list[1] = getLabel("weather_run_off_1");
                    list[2] = getLabel("weather_run_off_2");
                    break;

                case Constants.SPIN_IRRIGATED_QUE:
                    list = new String[3];
                    list[0] = getLabel("Please_select");
                    list[1] = getLabel("weather_did_u_irrigated_1");
                    list[2] = getLabel("weather_did_u_irrigated_2");
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


//    public String getDropdownKey(String dropdown, int selectedValue) {
//        String selectedKey = "";
//        String[] list = null;
//        try {
//            switch (dropdown) {
//
//                case Constants.SPIN_CERTIFICATE:
//                    list = new String[4];
//                    list[0] = "Please_select";
//                    list[1] = "profile_certificate_1";
//                    list[2] = "profile_certificate_2";
//                    list[3] = "profile_certificate_3";
//                    break;
//
//                case Constants.SPIN_IRRIGATION_SYS:
//                    list = new String[5];
//                    list[0] = "Please_select";
//                    list[1] = "irrigation_system_1";
//                    list[2] = "irrigation_system_2";
//                    list[3] = "irrigation_system_3";
//                    list[4] = "irrigation_system_4";
//                    break;
//
//                case Constants.SPIN_SOIL_TYPE:
//                    list = new String[4];
//                    list[0] = "Please_select";
//                    list[1] = "profile_soil_type_1";
//                    list[2] = "profile_soil_type_2";
//                    list[3] = "profile_soil_type_3";
//                    break;
//
//                case Constants.SPIN_SEED_TYPE:
//                    list = new String[2];
//                    list[0] = "Please_select";
//                    list[1] = "profile_seed_type_1";
//                    break;
//
//                case Constants.SPIN_FARMING_TYPE:
//                    list = new String[3];
//                    list[0] = "Please_select";
//                    list[1] = "profile_farming_type_1";
//                    list[2] = "profile_farming_type_2";
//                    break;
//
//               /* case Constants.SPIN_WEATHER:
//                    list = new String[5];
//                    list[0] = getLabel("Please_select");
//                    list[1] = getLabel("weather_option_text_1");
//                    list[2] = getLabel("weather_option_text_2");
//                    list[3] = getLabel("weather_option_text_3");
//                    list[4] = getLabel("weather_option_text_4");
//                    break;
//
//                case Constants.SPIN_RAIN_EVENT:
//                    list = new String[5];
//                    list[0] = getLabel("Please_select");
//                    list[1] = getLabel("weather_rain_event_1");
//                    list[2] = getLabel("weather_rain_event_2");
//                    list[3] = getLabel("weather_rain_event_3");
//                    list[4] = getLabel("weather_rain_event_4");
//                    break;
//
//                case Constants.SPIN_RUNOFF:
//                    list = new String[3];
//                    list[0] = getLabel("Please_select");
//                    list[1] = getLabel("weather_run_off_1");
//                    list[2] = getLabel("weather_run_off_2");
//                    break;
//
//                case Constants.SPIN_IRRIGATED_QUE:
//                    list = new String[3];
//                    list[0] = getLabel("Please_select");
//                    list[1] = getLabel("weather_did_u_irrigated_1");
//                    list[2] = getLabel("weather_did_u_irrigated_2");
//                    break;*/
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return list[selectedValue];
//    }


}
