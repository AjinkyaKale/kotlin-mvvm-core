package com.smartfarming.screenrender.manager;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

public class IrrigationManager {

    public static final String LAST_IRRIGATED_DATE = "Last_Irrigated_Date";
    public static final String IRRIGATION_DETAIL_ID = "1";


    // for weather options---
    public static final String Cloudy_weather = "Cloudy weather";
    public static final String Sun_without_wind = "Sun without wind";
    public static final String Sun_with_little_wind_and_high_temperature = "Sun with little wind and high temperature";
    public static final String Sun_with_dry_wind_and_high_temperature = "Sun with dry wind and high temperature";


    /// for Kc days after sowing
    public static final Double kc_1 = 0.5;
    public static final Double kc_2 = 0.8;
    public static final Double kc_3 = 1.15;
    public static final Double kc_4 = 1.75;

    /// for rain event
    public static final String No_rain = "No rain";
    public static final String Light_rain = "Light rain";
    public static final String Medium_rain = "Medium rain";
    public static final String Heavy_rain = "Heavy rain";


    /// for run off
    public static final String No_run_off = "No run off";
    public static final String Run_off = "Run off";

    // for irrigation_system
    public static final String Furrow_Irrigation = "Furrow Irrigation";
    public static final String Drip_Irrigation = "Drip Irrigation";
    public static final String Sprinkler_Irrigation = "Sprinkler Irrigation";


    public static HashMap<String, Double> mapWeatherOptions = new HashMap<>();
    public static HashMap<String, Double> mapRainEvent = new HashMap<>();
    public static HashMap<String, Double> mapRunOff = new HashMap<>();
    public static HashMap<String, Double> mapIrrigationMethod = new HashMap<>();

    public static int MAX_WEATHER_ROW = 10;


    /*public static int getPreviousSelection(String selected, String[] array){

        int index = 0;

        if (selected ==  null)
            return index;

        if (selected.equalsIgnoreCase(IrrigationFragment.PLEASE_SELECT)){
            index = 0;
        }else {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equalsIgnoreCase(selected)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }*/

    /**
     * get stage name for pest monitoring initial screen
     *
     * @param context
     * @param no_of_days
     * @return
     */
    public static String getStageNameForDAS(Context context, int no_of_days, String variety) {
        LanguageManager languageManager = LanguageManager.getInstance(context);
        try {
           /* IrrigationDetailModel detailModel = RealmController.with((Activity) context).
                    getIrrigationDetailRecord(IrrigationManager.IRRIGATION_DETAIL_ID);*/
            String stageName;
            switch (variety) {
                case "VARIETY_TYPE_1":
                    stageName = variety1StageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_2":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_3":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_4":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_5":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_6":
                    stageName = variety6And10StageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_7":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_8":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_9":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_10":
                    stageName = variety6And10StageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_11":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                case "VARIETY_TYPE_12":
                    stageName = varietyCommonStageName(languageManager, no_of_days);
                    if (stageName != null) return stageName;
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Nullable
    private static String varietyCommonStageName(LanguageManager languageManager, int no_of_days) {
        if (no_of_days >= -250 && no_of_days <= 20) {
            //return languageManager.getLabel("sprout_and_vegetative_growth_stage");
            return "STAGE1";
        } else if (no_of_days >= 21 && no_of_days <= 35) {
            //return languageManager.getLabel("tuber_initiation_stage");
            return "STAGE2";
        } else if (no_of_days >= 36 && no_of_days <= 75) {
            //return languageManager.getLabel("bulking_stage");
            return "STAGE3";
        } else if (no_of_days >= 76 && no_of_days <= 250) { // change  : earlier 95 was there instead of 250
            //return languageManager.getLabel("ripening_stage");
            return "STAGE4";
        }
        return null;
    }

    @Nullable
    private static String variety6And10StageName(LanguageManager languageManager, int no_of_days) {
        if (no_of_days >= -250 && no_of_days <= 15) {
            //return languageManager.getLabel("sprout_and_vegetative_growth_stage");
            return "STAGE1";
        } else if (no_of_days >= 16 && no_of_days <= 25) {
            //return languageManager.getLabel("tuber_initiation_stage");
            return "STAGE2";
        } else if (no_of_days >= 26 && no_of_days <= 55) {
            //return languageManager.getLabel("bulking_stage");
            return "STAGE3";
        } else if (no_of_days >= 56 && no_of_days <= 250) {
            // return languageManager.getLabel("ripening_stage");
            return "STAGE4";
        }
        return null;
    }

    @Nullable
    private static String variety1StageName(LanguageManager langaugeManager, int no_of_days) {
        if (no_of_days >= -250 && no_of_days <= 15) {
            //return langaugeManager.getLabel("sprout_and_vegetative_growth_stage");
            return "STAGE1";
        } else if (no_of_days >= 16 && no_of_days <= 25) {
            // return langaugeManager.getLabel("tuber_initiation_stage");
            return "STAGE2";
        } else if (no_of_days >= 26 && no_of_days <= 55) {
            //return langaugeManager.getLabel("bulking_stage");
            return "STAGE3";
        } else if (no_of_days >= 56 && no_of_days <= 250) {
            //return langaugeManager.getLabel("ripening_stage");
            return "STAGE4";
        }
        return null;
    }


    public static Double getKc(String difference, String varietyType) {
        Double diff = Double.parseDouble(difference);
        Double kc;
        switch (varietyType) {
            case "VARIETY_TYPE_1":
                kc = getKCForVariety1(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_2":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_3":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_4":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_5":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_6":
                kc = getKCForVariety6And10(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_7":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_8":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_9":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_10":
                kc = getKCForVariety6And10(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_11":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
            case "VARIETY_TYPE_12":
                kc = getKCForVarietyCommon(diff);
                if (kc != null) return kc;
                break;
        }
        return 0.0;
    }

    private static Double getKCForVariety6And10(Double diff) {
        if (0 < diff && 20 >= diff) {
            return kc_1;
        } else if (21 <= diff && 35 >= diff) {
            return kc_2;
        } else if (36 <= diff && 100 >= diff) {
            return kc_3;
        } else if (101 <= diff && 120 >= diff) {
            return kc_4;
        }
        return null;
    }

    private static Double getKCForVariety1(Double diff) {
        if (0 <= diff && 15 >= diff) {
            return kc_1;
        } else if (16 <= diff && 25 >= diff) {
            return kc_2;
        } else if (26 <= diff && 55 >= diff) {
            return kc_3;
        } else if (56 <= diff && 75 >= diff) {
            return kc_4;
        }
        return null;
    }

    private static Double getKCForVarietyCommon(Double diff) {
        if (0 <= diff && 20 >= diff) {
            return kc_1;
        } else if (21 <= diff && 35 >= diff) {
            return kc_2;
        } else if (36 <= diff && 75 >= diff) {
            return kc_3;
        } else if (76 <= diff && 95 >= diff) {
            return kc_4;
        }
        return null;
    }

    public static Double getWeatherOptionValue(String key) {

        if (TextUtils.isEmpty(key)) {
            return 0.0;
        }

        if (mapWeatherOptions.size() == 0) {

            mapWeatherOptions.put("1", 3.0);//Cloudy_weather
            mapWeatherOptions.put("2", 4.5);//Sun_without_wind
            mapWeatherOptions.put("3", 6.5);//Sun_with_little_wind_and_high_temperature
            mapWeatherOptions.put("4", 8.0);//Sun_with_dry_wind_and_high_temperature
        }

        return mapWeatherOptions.get(key);
    }


    public static Double getRainEventValue(String key) {

        if (TextUtils.isEmpty(key)) {
            return 0.0;
        }

        if (mapRainEvent.size() == 0) {
            mapRainEvent.put("0", 0.0);
            mapRainEvent.put("1", 10.0);//Light_rain
            mapRainEvent.put("2", 20.0);//Medium_rain
            mapRainEvent.put("3", 40.0);//Heavy_rain
        }

        return mapRainEvent.get(key);

    }


    public static Double getRunOffValue(String key) {

        if (TextUtils.isEmpty(key)) {
            return 0.0;
        }

        if (key.equals("0")) {
            return 0.0;
        }

        if (mapRunOff.size() == 0) {
            mapRunOff.put("1", 1.0);//No_run_off
            mapRunOff.put("2", 0.5);//Run_off
        }

        return mapRunOff.get(key);
    }


    public static Double getIrrMethodValue(String key) {

        if (TextUtils.isEmpty(key)) {
            return 0.0;
        }

        if (mapIrrigationMethod.size() == 0) {

            mapIrrigationMethod.put("1", 0.0);
            mapIrrigationMethod.put("2", 0.4);//Furrow_Irrigation
            mapIrrigationMethod.put("3", 0.6);//Sprinkler_Irrigation
            mapIrrigationMethod.put("4", 0.8);//Drip_Irrigation
        }

        return mapIrrigationMethod.get(key);
    }

    public static int getPreviousSelection(String selected, String[] array) {

        int index = 0;

        if (selected == null)
            return index;

//        if (selected.equalsIgnoreCase(IrrigationFragment.PLEASE_SELECT)){
        //Todo:Later uncomment above line and remove below line
        if (selected.equalsIgnoreCase("")) {
            index = 0;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equalsIgnoreCase(selected)) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

}
