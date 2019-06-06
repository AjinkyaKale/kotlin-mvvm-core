package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.activity.VarietySelectionActivity;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.LabelConstant;
import com.smartfarming.screenrender.util.Utility;

import static com.smartfarming.screenrender.util.LabelConstant.VARIETY_SELECTION_TITLE;

/**
 * Created by sopnil on 24/3/18.
 */

public class VarietyQuestionnaireFragment extends BaseFragment {


    @BindView(R2.id.txt_variety_selection_title)
    TextView txt_selection_title;

    @BindView(R2.id.txt_label_region)
    TextView txt_label_region;

    @BindView(R2.id.spinner_region)
    Spinner spinner_region;

    /*@BindView(R2.id.radio_group_method_type)
    RadioGroup radio_group_method_type;*/

    /*@BindView(R2.id.radio_btn_organic)
    RadioButton radio_btn_organic;*/

    /*@BindView(R2.id.radio_btn_conventional)
    RadioButton radio_btn_conventional;*/

    @BindView(R2.id.radio_group_crop_type)
    RadioGroup radio_group_crop_type;

    @BindView(R2.id.radio_btn_kharif)
    RadioButton radio_btn_kharif;

    @BindView(R2.id.radio_btn_rabi)
    RadioButton radio_btn_rabi;

    @BindView(R2.id.txt_label_purpose)
    TextView txt_label_purpose;

    @BindView(R2.id.spinner_purpose)
    Spinner spinner_purpose;

    @BindView(R2.id.txt_label_water_availability)
    TextView txt_label_water_availability;

    @BindView(R2.id.spinner_water_availability)
    Spinner spinner_water_availability;

//    @BindView(R2.id.txt_water_till)
//    TextView txt_water_till;

    @BindView(R2.id.button_show_options)
    Button button_show_options;

    @BindView(R2.id.txt_title_season)
    TextView txtTitleSeason;

    /*@BindView(R2.id.txt_title_method)
    TextView txtTitleMethod;*/

    @BindView(R2.id.ll_water_availability_spinner)
    LinearLayout llWaterAvailability;

    private LanguageManager mLanguageManager;
    private String[] mRegions;
    private String[] mPurpose;
    private String[] mWaterAvailability;
    View fragmentView;
    private boolean avoidWaterAvailability = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_variety_questionnaire, container, false);
            ButterKnife.bind(this, fragmentView);
            setLabels();
            init();
            setDataToSpinners();
            // Track screen by name on Fire-base server
            Utility.trackScreenByClassName(getActivity(), this.getClass());
        }
        return fragmentView;
    }

    private void init() {
        // set visibility of water availability on selection of crop type
        // if kharif , hide water availability
        // if rabi , show water availability
        radio_group_crop_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int cropType = radio_group_crop_type.getCheckedRadioButtonId();
                RadioButton cropRadio = (RadioButton) fragmentView.findViewById(cropType);
                if (cropRadio != null) {
                    if (cropRadio.getId() == R.id.radio_btn_kharif) {
                        setVisibilityOfWaterAvailability(View.GONE);
                        avoidWaterAvailability = true;
                    } else if (cropRadio.getId() == R.id.radio_btn_rabi) {
                        setVisibilityOfWaterAvailability(View.VISIBLE);
                        avoidWaterAvailability = false;
                    }
                }
            }
        });
    }

    private void setLabels() {
        mLanguageManager = LanguageManager.getInstance(getActivity());

        txt_selection_title.setText(mLanguageManager.getLabel(VARIETY_SELECTION_TITLE));
        txt_label_region.setText(mLanguageManager.getLabel(LabelConstant.REGION));
        //radio_btn_organic.setText(mLanguageManager.getLabel(LabelConstant.METHOD_ORGANIC));
        //radio_btn_conventional.setText(mLanguageManager.getLabel(LabelConstant.METHOD_CONVENTIONAL));
        radio_btn_kharif.setText(mLanguageManager.getLabel(LabelConstant.CROP_TYPE_KHARIF));
        radio_btn_rabi.setText(mLanguageManager.getLabel(LabelConstant.CROP_TYPE_RABI));
        txt_label_purpose.setText(mLanguageManager.getLabel(LabelConstant.PURPOSE));
        txt_label_water_availability.setText(mLanguageManager.getLabel(LabelConstant.WATER_AVAILABILITY));
        //txt_water_till.setText(mLanguageManager.getLabel(LabelConstant.WATER_TILL_DATE));
        button_show_options.setText(mLanguageManager.getLabel(LabelConstant.SHOW_OPTIONS));
        txtTitleSeason.setText(mLanguageManager.getLabel(LabelConstant.SEASON));

//        Utility.trackScreenByScreenName(getActivity(), mLanguageManager.getLabel(VARIETY_SELECTION_TITLE));

    }

    @Override
    public void onResume() {
        super.onResume();

        ((VarietySelectionActivity) getActivity()).setToolbarTitle(mLanguageManager.getLabel(VARIETY_SELECTION_TITLE));
    }

    @OnClick(R2.id.button_show_options)
    public void showVarietySuggestions() {
        if (validateOptions()) {
            if (avoidWaterAvailability == false) {
                VarietySuggestionFragment instance = VarietySuggestionFragment.newInstance(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.REGION_TYPE)[spinner_region.getSelectedItemPosition()],
                        /*getMethodType(),*/
                        getCropType(),
                        com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.FARMING_TYPE)[spinner_purpose.getSelectedItemPosition()],
                        com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.WATER_AVAILABILITY_TYPE)[spinner_water_availability.getSelectedItemPosition()]);
                ((VarietySelectionActivity) getActivity()).replaceFragment(instance);
            } else if (avoidWaterAvailability == true) {
                VarietySuggestionFragment instance = VarietySuggestionFragment.newInstance(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.REGION_TYPE)[spinner_region.getSelectedItemPosition()],
                        getCropType(),
                        com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.FARMING_TYPE)[spinner_purpose.getSelectedItemPosition()]);
                ((VarietySelectionActivity) getActivity()).replaceFragment(instance);
            }
        }
    }

    /*private String getMethodType() {
        int methodType = radio_group_method_type.getCheckedRadioButtonId();
        RadioButton methodRadio = (RadioButton) fragmentView.findViewById(methodType);
        if (methodRadio != null) {
            if (methodRadio.getId() == R.id.radio_btn_conventional) {
                return com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.METHOD_TYPE)[2];
            } else if (methodRadio.getId() == R.id.radio_btn_organic) {
                return com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.METHOD_TYPE)[1];
            }
        }
        return "";
    }*/

    private String getCropType() {
        int cropType = radio_group_crop_type.getCheckedRadioButtonId();
        RadioButton cropRadio = (RadioButton) fragmentView.findViewById(cropType);
        if (cropRadio != null) {
            if (cropRadio.getId() == R.id.radio_btn_kharif) {
                return com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.CROP_TYPE)[1];
            } else if (cropRadio.getId() == R.id.radio_btn_rabi) {
                return com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.CROP_TYPE)[2];
            }
        }
        return "";
    }

    private void setVisibilityOfWaterAvailability(int visibility) {

        if (View.GONE == visibility) {
            llWaterAvailability.setVisibility(View.GONE);
        } else if (View.VISIBLE == visibility) {
            llWaterAvailability.setVisibility(View.VISIBLE);
        }
    }

    private void setDataToSpinners() {

        mRegions = LanguageManager.getInstance(getActivity()).getArrayOfLabels(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.REGION_TYPE));
        spinner_region.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, mRegions));

        mPurpose = LanguageManager.getInstance(getActivity()).getArrayOfLabels(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.FARMING_TYPE));
        spinner_purpose.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, mPurpose));

        mWaterAvailability = LanguageManager.getInstance(getActivity()).getArrayOfLabels(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.WATER_AVAILABILITY_TYPE));
        spinner_water_availability.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, mWaterAvailability));

        if (getArguments() != null) {
            if (getArguments().containsKey(VarietySelectionActivity.ARGS_REGION_PRE_FILLED)) {
                spinner_region.setSelection(getArguments().getInt(VarietySelectionActivity.ARGS_REGION_PRE_FILLED));
            }
            if (getArguments().containsKey(VarietySelectionActivity.ARGS_PURPOSE_PRE_FILLED)) {
                spinner_purpose.setSelection(getArguments().getInt(VarietySelectionActivity.ARGS_PURPOSE_PRE_FILLED));
            }
            if (getArguments().containsKey(VarietySelectionActivity.ARGS_SEASON_PRE_FILLED)) {
                if (getArguments().getString(VarietySelectionActivity.ARGS_SEASON_PRE_FILLED).equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.CROP_TYPE)[1])) {
                    radio_btn_kharif.setChecked(true);
                } else if (getArguments().getString(VarietySelectionActivity.ARGS_SEASON_PRE_FILLED).equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getLabelKeyArray(Constants.CROP_TYPE)[2])) {
                    radio_btn_rabi.setChecked(true);
                }
            }
        }
    }

    private boolean validateOptions() {

        // region  validation
        if (spinner_region.getSelectedItemPosition() == 0) {
            AppUtility.showToastBykey(getActivity(), LabelConstant.REGION_REQUIRED);
            return false;
        }

       /* // method type
        int methodType = radio_group_method_type.getCheckedRadioButtonId();
        RadioButton methodRadio = (RadioButton) fragmentView.findViewById(methodType);
        if (methodRadio != null) {

            if (methodRadio.getId() == R.id.radio_btn_organic) {

            } else if (methodRadio.getId() == R.id.radio_btn_conventional) {

            } else {
                AppUtility.showToastBykey(getActivity(), LabelConstant.METHOD_TYPE_REQUIRED);
                return false;
            }
        } else {
            AppUtility.showToastBykey(getActivity(), LabelConstant.METHOD_TYPE_REQUIRED);
            return false;
        }*/


        // crop type
        int cropType = radio_group_crop_type.getCheckedRadioButtonId();
        RadioButton cropRadio = (RadioButton) fragmentView.findViewById(cropType);
        if (cropRadio != null) {
            if (cropRadio.getId() == R.id.radio_btn_kharif) {

            } else if (cropRadio.getId() == R.id.radio_btn_rabi) {

            } else {
                AppUtility.showToastBykey(getActivity(), LabelConstant.CROP_TYPE_REQUIRED);
                return false;
            }
        } else {
            AppUtility.showToastBykey(getActivity(), LabelConstant.CROP_TYPE_REQUIRED);
            return false;
        }


        // purpose  validation
        if (spinner_purpose.getSelectedItemPosition() == 0) {
            AppUtility.showToastBykey(getActivity(), LabelConstant.PURPOSE_REQUIRED);
            return false;
        }

        if (avoidWaterAvailability == false) {
            // water  validation
            if (spinner_water_availability.getSelectedItemPosition() == 0) {
                AppUtility.showToastBykey(getActivity(), LabelConstant.WATER_AVAILABILITY_REQUIRED);
                return false;
            }
        }


        return true;
    }

}




