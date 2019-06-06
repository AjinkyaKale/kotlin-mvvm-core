package com.smartfarming.screenrender.manager;import android.content.Context;import android.os.Bundle;import android.support.annotation.Nullable;import android.support.v4.app.Fragment;import android.text.TextUtils;import com.smartfarming.screenrender.database.RealmController;import com.smartfarming.screenrender.fragment.*;import com.smartfarming.screenrender.model.*;import com.smartfarming.screenrender.util.Constants;import com.smartfarming.screenrender.util.Utility;import io.realm.Realm;import io.realm.RealmList;import io.realm.RealmResults;import java.util.List;/** * Created by sopnil on 28/2/18. */public class ScreenManager {    public static final String MAJOR_KEY = "Major";    public static final String MODERATE_KEY = "Moderate";    public static final String YES_BUTTON_KEY = "Yes";    public static final String NO_BUTTON_KEY = "No";    public static final String CHANGE_BUTTON_KEY = "Change";    /**     * To get screen object by screen_id     *     * @param screen_id     * @return     */    public static ScreenModel getScreenObject(String screen_id) {        System.out.println("Screen Id : " + screen_id);        RealmResults<ScreenInfoModel> screenModels = RealmController.with().getScreenModels();        ScreenModel screenModel = screenModels.where().equalTo("id", screen_id).findFirst().getScreenModel();        return screenModel;    }    /**     * Get launch fragment for given screen_id     *     * @param screen_id     * @return     */    @Nullable    public static Fragment getFragmentForId(String screen_id) {        String title_label; //"lang_lable";        ScreenModel screenModel = getScreenObject(screen_id);        if (screenModel == null) return null;        title_label = screenModel.getTitle();        String screen_type = screenModel.getScreenType();//        screenModel.ge        Fragment fragment = getScreenFragment(screen_type, screen_id, title_label);        return fragment;    }    /**     * get Fragment for the screen type     *     * @param screen_type     * @param screen_id     * @return     */    public static Fragment getScreenFragment(String screen_type, String screen_id, String title_label) {        Fragment fragment;        switch (screen_type) {            case "images_question_yes_no_more_info_buttons":                fragment = QuestionnaireFragment.newInstance();                break;            case "popup_title_text_question_images":                fragment = new PopupTitleTextQuestionImg();                break;            case "popup_text_question_images":                fragment = new PopTextQuestionImg();                break;            case "img_desc_btn":                fragment = ImageDescriptionBtnFragment.newInstance();                break;            case "images_text_question_yes_no_buttons":                fragment = ImageTextQueYesNoFragment.newInstance();                break;            case "title_images_question_text_yes_no_buttons":                fragment = ETLDescriptionFragment.newInstance();                break;            case "images_multiple_questions_with_yes_no_buttons":                fragment = PheromoneTrapQuestionFragment.newInstance();                break;            case "popup_text_buttons":                fragment = new PopupGoMeasures();                break;            case "popup_question_text_yes_no_buttons":                fragment = new PopSeeLarvae();                break;            case "tab_general_info_pest":                fragment = MeasuresFragment.newInstance();                break;            case "tab_biological_pest":                fragment = MeasuresFragment.newInstance();                break;            case "tab_chemical_control_pest":                fragment = MeasuresFragment.newInstance();                break;            case "title_text_title_text_images_yes_no_buttons":            case "title_text_title_text_images_buttons":                fragment = PestPageBollwormsFragment.newInstance();                break;//           /* case "title_text_title_text_images_buttons"://                fragment = PestModerateFragment.newInstance();//                break;*///            case "images_title":                fragment = SubStagesStageWiseGapFragment.newInstance();                break;            case "text":                fragment = DescriptionFragment.newInstance();                break;            case "text_rating":                fragment = DescriptionRatingFragment.newInstance();                break;            case "images_text":                fragment = ImageDescriptionFragment.newInstance();                break;            case "images_text_rating":                fragment = ImageTextRatingFragment.newInstance();                break;            case "images_text_question_yes_no_buttons_rating":                fragment = AdviceImageTextQuestionYesNoRatingFragment.newInstance();                break;            case "title":                fragment = TitleListFragment.newInstance();                break;            case "popup_title_text_buttons":                fragment = new PopupETL();                break;            case "images_text_title":                fragment = ImageTextTitleFragment.newInstance();                break;            case "images":                fragment = ImagesFragment.newInstance();                break;            case "field_layout":                fragment = FieldLayoutFragment.newInstance();                break;            case "title_images_text_insert_pheromone_1":            case "title_images_text_insert_pheromone_2":            case "title_images_text_insert_pheromone_3":            case "title_images_text_insert_pheromone_4":            case "pheromone_trap":                fragment = PheromoneTrapFragment.newInstance();                break;            case "variety_information":                fragment = new VarietyInformationFragment();                break;            case "launching_informative":                fragment = new LaunchInfoFragment();                break;            case "title_images_text_insert_pheromone_trap":                fragment = new TitleImageTextInsertPheromoneTrap();                break;            default:                fragment = DefaultFragment.newInstance();                break;        }        Bundle bundle = new Bundle();        bundle.putString(Constants.SCREEN_ID_KEY, screen_id);        bundle.putString(Constants.SCREEN_TITLE_KEY, title_label);        fragment.setArguments(bundle);        return fragment;    }    /**     * get all image names from screen object     *     * @param imageModels     * @return     */    public static String[] getImageNameArray(RealmList<ImageModel> imageModels) {        String[] imageArr = new String[imageModels.size()];        for (int i = 0; i < imageModels.size(); i++) {            imageArr[i] = imageModels.get(i).getName();        }        return imageArr;    }    /**     * get all questions from screen object     *     * @param screenModel     * @return     */    public static List<TextModel> getQuestionArray(ScreenModel screenModel) {        RealmList<TextModel> list = screenModel.getTextModels();        RealmResults<TextModel> results = list.where().equalTo("type", "question").findAll();        return results;    }    /**     * get Selection answer for given screen id     *     * @param screenId     * @return     */    public static String getSelectedFeedback(String screenId) {        RatingCommentModel ratingCommentModel = RealmController.getInstance().getRatingCommentForScreenId(screenId);        if (ratingCommentModel != null) {            return ratingCommentModel.getSelectedButton();        }        return "";    }    /**     * insert feedback(yes/no ) to the table     * if already present update with the same screenId     *     * @param screenId     * @param selectedButton     */    public static void setFeedbackForScreenId(String screenId, String selectedButton) {        RatingCommentModel ratingCommentModel;        ratingCommentModel = RealmController.getInstance().getRatingCommentForScreenId(screenId);        Realm realm = Realm.getDefaultInstance();        realm.beginTransaction();        if (ratingCommentModel != null) {            if (!selectedButton.equals(ScreenManager.CHANGE_BUTTON_KEY)) {                ratingCommentModel.setSelectedButton(selectedButton);            } else {                ratingCommentModel.setSelectedButton("");            }        } else {            ratingCommentModel = new RatingCommentModel();            ratingCommentModel.setScreenId(screenId);            if (!selectedButton.equals(ScreenManager.CHANGE_BUTTON_KEY)) {                ratingCommentModel.setSelectedButton(selectedButton);            } else {                ratingCommentModel.setSelectedButton("");            }        }        ratingCommentModel.setSync(false);        realm.commitTransaction();        ScreenManager.saveFeedbackForScreen(ratingCommentModel);    }    /**     * insert or update answerButtonModel     *     * @param ratingCommentModel     */    public static void saveFeedbackForScreen(RatingCommentModel ratingCommentModel) {        Realm realm = RealmController.getInstance().getRealm();        realm.beginTransaction();        realm.copyToRealmOrUpdate(ratingCommentModel);        realm.commitTransaction();    }    /**     * update rating comment     *     * @param ratingCommentModel     */    public static void saveRatingComment(RatingCommentModel ratingCommentModel) {        Realm realm = RealmController.getInstance().getRealm();        realm.beginTransaction();        realm.copyToRealmOrUpdate(ratingCommentModel);        realm.commitTransaction();    }    /**     * get title list from screen object     *     * @param screenModel     * @return     */    public static List<TextModel> getTitleArray(ScreenModel screenModel) {        RealmList<TextModel> list = screenModel.getTextModels();        RealmResults<TextModel> results = list.where().equalTo("type", "title").findAll();        return results;    }    /**     * insert or update pheromone trap date for screen id     *     * @param pheromoneTrapDateModel     */    public static void savePheromoneTrapDate(PheromoneTrapDateModel pheromoneTrapDateModel) {        Realm realm = RealmController.getInstance().getRealm();        realm.beginTransaction();        realm.copyToRealmOrUpdate(pheromoneTrapDateModel);        realm.commitTransaction();    }    /**     * get stage name for irrigationscreen     *     * @param context     * @param no_of_days     * @return     */    /*public static String getStageNameForDAS(Context context, int no_of_days) {        LanguageManager languageManager = LanguageManager.getInstance(context);        try {           *//* IrrigationDetailModel detailModel = RealmController.with((Activity) context).                    getIrrigationDetailRecord(IrrigationManager.IRRIGATION_DETAIL_ID);*//*            if (0 > no_of_days) {                return languageManager.getLabel("stage_1");            } else if (no_of_days >= 0 && no_of_days <= 20) {                return languageManager.getLabel("stage_2");            } else if (no_of_days >= 21 && no_of_days <= 50) {                return languageManager.getLabel("stage_3");            } else if (no_of_days >= 51 && no_of_days <= 80) {                return languageManager.getLabel("stage_4");            } else if (no_of_days >= 81 && no_of_days <= 120) {                return languageManager.getLabel("stage_5");            } else if (no_of_days >= 121 && no_of_days <= 150) {                return languageManager.getLabel("stage_6");            } else if (150 > no_of_days) {                return languageManager.getLabel("stage_7");            }        } catch (Exception e) {            e.printStackTrace();        }        return "";    }*/    /**     * get stage (int) using DAS to launch initial     * fragment for identification     *     * @param context     * @return     */    public static String getStageForDAS(Context context) {        int DAS = 0;        UserModel userModel = RealmController.with().getUser();        if (userModel != null) {            if (!TextUtils.isEmpty(userModel.getSowingDate())) {                DAS = Utility.daysBetweenTwoDates(Utility.getDateFromTimeStamp(Long.valueOf(userModel.getSowingDate()), Constants.DATE_FORMAT), Utility.getCurrentDate(), Constants.DATE_FORMAT_DD_MM_YYYY);            }        }        String stageName = IrrigationManager.getStageNameForDAS(context, DAS, userModel.getVarietyKey());        IdentificationModel identificationModel = RealmController.getInstance().getIdentificationByVariety(userModel.getVarietyKey());        String screenID = "";        if (stageName.equalsIgnoreCase(Constants.STAGE_SPROUT_AND_VEGETATIVE_GROWTH_STAGE)) {            screenID = identificationModel.getSproutNVegetativeGrowthStage();        } else if (stageName.equalsIgnoreCase(Constants.STAGE_TUBER_INITIATION_STAGE)) {            screenID = identificationModel.getTuberInitiationStage();        } else if (stageName.equalsIgnoreCase(Constants.STAGE_BULKING_STAGE)) {            screenID = identificationModel.getBulkingStage();        } else if (stageName.equalsIgnoreCase(Constants.STAGE_RIPENING_STAGE)) {            screenID = identificationModel.getRipeningStage();        }        return screenID;    }    // pre showing screen methods    public static String getScreenForPreSowing(String screenFor) {        //UserModel userModel = RealmController.with().getUser();        PreSowingModel preSowing = RealmController.getInstance().getPreSowingModel();        String screenID = "";        if (screenFor.equalsIgnoreCase(Constants.CHOOSE_YOUR_VARIETY)) {            screenID = preSowing.getVariety();        } else if (screenFor.equalsIgnoreCase(Constants.SEED_POTATO_HANDLING)) {            screenID = preSowing.getSeedPotatoHandling();        } else if (screenFor.equalsIgnoreCase(Constants.FARM_LAYOUT)) {            screenID = preSowing.getFarmLayout();        } else if (screenFor.equalsIgnoreCase(Constants.LAND_PREPARATION)) {            screenID = preSowing.getLandPreparation();        } else if (screenFor.equalsIgnoreCase(Constants.SPACING_SCREEN)) {            screenID = preSowing.getSpacingDensity();        }        return screenID;    }}