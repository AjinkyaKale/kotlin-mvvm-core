package com.smartfarming.screenrender.database;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import com.smartfarming.screenrender.model.*;
import com.smartfarming.screenrender.realmmodule.LibraryPojos;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.LabelConstant;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmController {

    private final RealmConfiguration realmLibraryConfig;

    private static RealmController instance;
    private final Realm realm;

    private final long DATABASE_VERSION = 2;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
        realmLibraryConfig = null;
    }

    public RealmController() {

        realmLibraryConfig = new RealmConfiguration.Builder()     // The app is responsible for calling `Realm.init(Context)`
                .name("screenRender")                 // So always use a unique name
                .modules(new LibraryPojos())           // Always use explicit modules in library projects
                .migration(new Migration())
                .schemaVersion(DATABASE_VERSION)
                .build();

        realm = Realm.getInstance(realmLibraryConfig);
        //realm = Realm.getDefaultInstance();
    }

    public static RealmController with() {

        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }


    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }


    public void insertUser(UserModel userModel) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(userModel);
        realm.commitTransaction();
    }


    public UserModel getUser() {
        return realm.where(UserModel.class).findFirst();
    }

    public RealmList<StateModel> getStates() {
        return realm.where(ConfigData.class).findAll().get(0).getListOfStates();
    }

    public void insertCommonConfigurations(ConfigData configData) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(configData);
        realm.commitTransaction();
    }


    public int getRegionIndexViaState(String state) {
        int index = 0;
        RealmList<StateModel> list = getStates();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStateName().equalsIgnoreCase(state)) {
                String regionKey = list.get(i).getRegion();
                index = realm.where(ConfigData.class).findAll().get(0).getListRegionTypes().indexOf(regionKey);

                break;
            }
        }
        return index;
    }

    //-----------------------Get Screen (ContentDataModel.java)--------------------------------------

//    public RealmResults<ScreenModel> getScreenModels() {
//        return realm.where(ScreenModel.class).findAll();
//    }

    public RealmResults<ScreenInfoModel> getScreenModels() {
        return realm.where(ScreenInfoModel.class).findAll();
    }

    public void insertScreensInDB(RealmList<ScreenInfoModel> list) {
        realm.beginTransaction();
        realm.insertOrUpdate(list);
        realm.commitTransaction();
    }


    //-----------------------Get Ratting & Comment --------------------------------------------------
    //-----------------------------------------------------------------------------------------------


    public RatingCommentModel getRatingCommentForScreenId(String screenId) {
        return realm.where(RatingCommentModel.class).equalTo("screenId", screenId).findFirst();
    }

    public RealmResults<RatingCommentModel> getAllRatingComment() {
        return realm.where(RatingCommentModel.class).findAll();
    }

    public void updateRatingCommentStatus(String screenId, boolean isSync) {
        try {
            realm.where(RatingCommentModel.class).equalTo("screenId", screenId).findFirst().setSync(isSync);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public RealmResults<ActionAdviceModel> getActionAdviceModels() {
        return realm.where(ActionAdviceModel.class).findAll();

    }


    //-----------------------Get pheromone trap screen model--------------------------------------------
    //-----------------------------------------------------------------------------------------------

    public PheromoneTrapDateModel getPheromoneTrapDate(String screenId) {
        return realm.where(PheromoneTrapDateModel.class).equalTo("screenId", screenId).findFirst();
    }

    public RealmResults<PheromoneTrapDateModel> getAllPheromoneTraps() {
        return realm.where(PheromoneTrapDateModel.class).findAll();
    }


    //-------------------------------Identification ---------------------------------
    public IdentificationModel getIdentificationByVariety(String varietyTypeKey) {

        return realm.where(IdentificationModel.class).equalTo("varietyType", varietyTypeKey).findFirst();

    }

    public String[] getLabelKeyArray(String type) {

        RealmResults<ConfigData> result = realm.where(ConfigData.class).findAll();
        String[] keyArray = null;

        switch (type) {

            case Constants.SOIL_TYPE:
                keyArray = getKeysArray(result.get(0).getListSoilTypes());
                break;

            case Constants.RAIN_TYPE:
                keyArray = getKeysArray(result.get(0).getListRainTypes());
                break;

            case Constants.WEATHER_TYPE:
                keyArray = getKeysArray(result.get(0).getListWeatherTypes());
                break;


            case Constants.TEMPERATURE_TYPE:
                keyArray = getKeysArray(result.get(0).getListTemperatureTypes());
                break;


            case Constants.REGION_TYPE:
                keyArray = getKeysArray(result.get(0).getListRegionTypes());
                break;

            case Constants.VARIETY_TYPE:
                keyArray = getKeysArray(result.get(0).getListVarietyTypes());
                break;

            case Constants.STORAGE_TYPE:
                keyArray = getKeysArray(result.get(0).getListStorageTypes());
                break;


            case Constants.FERTILIZER_TYPE:
                keyArray = getKeysArray(result.get(0).getListFertilizerTypes());
                break;

            case Constants.SPRAYING_TYPE:
                keyArray = getKeysArray(result.get(0).getListSprayingTypes());
                break;


            case Constants.IRRIGATION_TYPE:
                keyArray = getKeysArray(result.get(0).getListIrrigationTypes());
                break;


            case Constants.FARMING_TYPE:
                keyArray = getKeysArray(result.get(0).getListPurposeTypes());
                break;


            case Constants.WATER_AVAILABILITY_TYPE:
                keyArray = getKeysArray(result.get(0).getListWaterAvailability());
                break;

            case Constants.CROP_TYPE:
                keyArray = getKeysArray(result.get(0).getListCropTypes());
                break;

            case Constants.METHOD_TYPE:
                keyArray = getKeysArray(result.get(0).getListMethodTypes());
                break;

            case Constants.ADVICE_TYPE:
                keyArray = getKeysArray(result.get(0).getListAdviceTypes());
                break;


            case Constants.CERTIFICATION_TYPE:
                keyArray = getKeysArray(result.get(0).getListCertificationTypes());
                break;

        }
        return keyArray;

    }

    public String[] getKeysArray(RealmList<String> list) {
        String[] keyArray = null;
        keyArray = new String[list.size() + 1];
        keyArray[0] = LabelConstant.PLEASE_SELECT;
        for (int i = 0; i < list.size(); i++) {
            keyArray[i + 1] = list.get(i);
        }
        return keyArray;
    }


    public String getDropDownKey(String dropdown, int selectedValue) {

        RealmResults<ConfigData> result = realm.where(ConfigData.class).findAll();
        String dropDownKey = "";
        selectedValue = selectedValue - 1;// as there is a default text please select in every drop down
        switch (dropdown) {
            case Constants.SPIN_VARIETY:
                dropDownKey = result.get(0).getListVarietyTypes().get(selectedValue);
                break;

            case Constants.SPIN_PURPOSE:
                dropDownKey = result.get(0).getListPurposeTypes().get(selectedValue);
                break;

            case Constants.SPIN_CERTIFICATE:
                dropDownKey = result.get(0).getListCertificationTypes().get(selectedValue);
                break;


            case Constants.SPIN_SOIL_TYPE:
                dropDownKey = result.get(0).getListSoilTypes().get(selectedValue);
                break;

            case Constants.SPIN_IRRIGATION_SYS:
                dropDownKey = result.get(0).getListIrrigationTypes().get(selectedValue);
                break;

            case Constants.SPIN_STORAGE:
                dropDownKey = result.get(0).getListStorageTypes().get(selectedValue);
                break;

        }

        return dropDownKey;

    }


    //--------------------------------------Pre Sowing----queries--------------------------------

    public PreSowingModel getPreSowingModel() {
        //return realm.where(PreSowingModel.class).findFirst();
        return realm.where(ConfigData.class).findAll().get(0).getPreSowingModel();
    }


    //-------------------------------------Variety Selection------------------------------------

    public RealmList<VarietyModel> getPossibleVarieties(String region, /*String method,*/ String crop, String purpose, String waterAvalability) {

        RealmList<VarietyModel> listOfMatchedVariety = new RealmList<>();

        RealmResults<VarietySelection> varietySelections = realm.where(VarietySelection.class).findAll();

        for (VarietySelection selection : varietySelections) {
            if (selection.getListRegionTypes().contains(region)
                    /*&& selection.getListMethodTypes().contains(method)*/
                    && selection.getListCropTypes().contains(crop)
                    && selection.getListPurposeTypes().contains(purpose)
                    && selection.getListWaterAvailability().contains(waterAvalability)) {

                listOfMatchedVariety.add(getVarietyByType(selection.getVarietyType()));
            }
        }
        return listOfMatchedVariety;
    }

    public RealmList<VarietyModel> getPossibleVarieties(String region, String crop, String purpose) {

        RealmList<VarietyModel> listOfMatchedVariety = new RealmList<>();

        RealmResults<VarietySelection> varietySelections = realm.where(VarietySelection.class).findAll();

        for (VarietySelection selection : varietySelections) {
            if (selection.getListRegionTypes().contains(region)
                    && selection.getListCropTypes().contains(crop)
                    && selection.getListPurposeTypes().contains(purpose)) {

                listOfMatchedVariety.add(getVarietyByType(selection.getVarietyType()));
            }
        }
        return listOfMatchedVariety;
    }

    public VarietySelection getVarietySelectionSetting(String varietyName) {

        return realm.where(VarietySelection.class).equalTo("VarietyType", varietyName).findFirst();
    }

    public RealmList<VarietySelection> getVarietySelections() {

        RealmList<VarietySelection> varietySelections = new RealmList<VarietySelection>();
        RealmResults<VarietySelection> results = realm.where(VarietySelection.class).findAll();
        varietySelections.addAll(results.subList(0, results.size()));
        return varietySelections;
    }

    public RealmList<VarietyModel> getAllVarieties() {
        RealmList<VarietyModel> results = new RealmList<VarietyModel>();
        RealmResults<VarietyModel> realmResultList = realm.where(VarietyModel.class).findAll();
        results.addAll(realmResultList.subList(0, realmResultList.size()));
        return results;
    }

    public RealmList<String> getVarietyTypes() {
        ConfigData configData = realm.where(ConfigData.class).findFirst();
        return configData.getListVarietyTypes();
    }

    public VarietyModel getVarietyByType(String varietyType) {
        return realm.where(VarietyModel.class).equalTo("variety", varietyType).findFirst();
    }
    //--------------------------------Launch module--------------------------

    public String getScreenIdByLaunchModule(String moduleName) {

        if (moduleName.equalsIgnoreCase(Constants.LEARN_HOW_TO_SPRAY)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getLearnHowToSpray();
        } else if (moduleName.equalsIgnoreCase(Constants.SPRAY_MANCOZEB)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getMancozeb();
        } else if (moduleName.equalsIgnoreCase(Constants.SPRAY_REVUS)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getRevus();
        } else if (moduleName.equalsIgnoreCase(Constants.SPRAY_NEEM) || moduleName.equalsIgnoreCase(Constants.NEEM)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getNeemSpray();
        } else if (moduleName.equalsIgnoreCase(Constants.SPRAY_CURATIVE)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getCurativeSpray();
        } else if (moduleName.equalsIgnoreCase(Constants.ORGANIC)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getOrganic();
        } else if (moduleName.equalsIgnoreCase(Constants.IRRIGATION)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getIrrigation();
        } else if (moduleName.equalsIgnoreCase(Constants.FERTILIZER)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getFertilizer();
        } else if (moduleName.equalsIgnoreCase(Constants.IDENTIFICATION)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getIdentification();
        } else if (moduleName.equalsIgnoreCase(Constants.SPRAY_SECTIN)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getSectinSpray();
        } else if (moduleName.equalsIgnoreCase(Constants.BLIGHT_DAMAGE)) {
            return realm.where(ConfigData.class).findFirst().getLaunchModule().getBlightDamage();
        }

        return null;
    }

    public String getInformationScreenById(String moduleName) {

        if (moduleName.equalsIgnoreCase(Constants.ABOUT_US)) {
            return realm.where(ConfigData.class).findFirst().getInformationModel().getAboutUs();
        }
        return null;
    }

    public String getOfflineMsgKey() {
        RealmList<String> realmList = new RealmList();
        realmList = realm.where(ConfigData.class).findFirst().getSprayingOfflineMessage();
        if (realmList.size() > 0) {
            return realmList.get(0);
        }
        return "";
    }
}
