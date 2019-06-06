package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.activity.VarietySelectionActivity;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.VarietyModel;
import com.smartfarming.screenrender.model.VarietySelection;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.LabelConstant;
import com.smartfarming.screenrender.util.OnDialogButtonClickListener;
import io.realm.RealmList;
import org.json.JSONObject;

import static com.smartfarming.screenrender.util.LabelConstant.VARIETY_SUGGESTION_TITLE;

/**
 * Created by sopnil on 24/3/18.
 */

public class VarietySuggestionFragment extends BaseFragment {

    public static final String REGION_TYPE = "region_type";
    public static final String METHOD_TYPE = "method_type";
    public static final String CROP_TYPE = "crop_type";
    public static final String PURPOSE = "purpose";
    public static final String WATER_AVAILABILITY = "water_availability";


    @BindView(R2.id.txt_label_my_farm)
    TextView txtLabelMyFarm;
    @BindView(R2.id.txt_label_region)
    TextView txtLabelRegion;
    @BindView(R2.id.txt_farm_details)
    TextView txtFarmDetails;
    @BindView(R2.id.txt_label_possible_varieties)
    TextView txtLabelPossibleVarieties;
    @BindView(R2.id.txt_label_possible_varieties_sub_text)
    TextView txtLabelPossibleVarietiesSubText;
    @BindView(R2.id.ll_container_possible_varieties)
    LinearLayout llContainerPossibleVarieties;
    @BindView(R2.id.txt_label_other_varieties)
    TextView txtLabelOtherVarieties;
    @BindView(R2.id.ll_container_other_varieties)
    LinearLayout llContainerOtherVarieties;
    @BindView(R2.id.txt_change_information)
    TextView txtChangeInformation;

    /*@BindView(R2.id.txt_label_possible_varieties_sub_text)
    TextView txtSubHeaderPossibleVarieties;*/

    private View fragmentView;
    private LanguageManager mLanguageManager;
    private JSONObject varietyJsonObject;

    public static VarietySuggestionFragment newInstance(String regionType, String methodType, String cropType, String purpose, String waterAvailability) {
        Bundle args = new Bundle();
        VarietySuggestionFragment fragment = new VarietySuggestionFragment();
        args.putString(REGION_TYPE, regionType);
        args.putString(METHOD_TYPE, methodType);
        args.putString(CROP_TYPE, cropType);
        args.putString(PURPOSE, purpose);
        args.putString(WATER_AVAILABILITY, waterAvailability);
        fragment.setArguments(args);
        return fragment;
    }

    // without method type
    public static VarietySuggestionFragment newInstance(String regionType, String cropType, String purpose, String waterAvailability) {
        Bundle args = new Bundle();
        VarietySuggestionFragment fragment = new VarietySuggestionFragment();
        args.putString(REGION_TYPE, regionType);
        args.putString(CROP_TYPE, cropType);
        args.putString(PURPOSE, purpose);
        args.putString(WATER_AVAILABILITY, waterAvailability);
        fragment.setArguments(args);
        return fragment;
    }

    // without water Availability
    public static VarietySuggestionFragment newInstance(String regionType, String cropType, String purpose) {
        Bundle args = new Bundle();
        VarietySuggestionFragment fragment = new VarietySuggestionFragment();
        args.putString(REGION_TYPE, regionType);
        args.putString(CROP_TYPE, cropType);
        args.putString(PURPOSE, purpose);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_variety_suggestions, container, false);
        ButterKnife.bind(this, fragmentView);
        //((VarietySelectionActivity) getActivity()).setToolbar(getString(R.string.pre_sowing_title));
        setLabel();
        setFarmDetails();
        addPossibleVarieties();
        // addOtherVarieties();
        return fragmentView;
    }

    private void setLabel() {

        mLanguageManager = LanguageManager.getInstance(getActivity());

        txtLabelMyFarm.setText(mLanguageManager.getLabel(LabelConstant.MY_FARM));
        txtLabelRegion.setText(mLanguageManager.getLabel(LabelConstant.REGION));
        txtLabelPossibleVarieties.setText(mLanguageManager.getLabel(LabelConstant.POSSIBLE_VARIETIES));
        txtLabelPossibleVarietiesSubText.setText(mLanguageManager.getLabel(LabelConstant.POSSIBLE_VARIETIES_SUB_TEXT));
        txtLabelOtherVarieties.setText(mLanguageManager.getLabel(LabelConstant.OTHER_VARIETIES));
        txtChangeInformation.setText(mLanguageManager.getLabel(LabelConstant.CHANGE_VARIETY_INFORMATION));

    }

    @Override
    public void onResume() {
        super.onResume();

        ((VarietySelectionActivity) getActivity()).setToolbarTitle(mLanguageManager.getLabel(VARIETY_SUGGESTION_TITLE));
    }

    private void setFarmDetails() {


        if (getArguments() != null) {
            String region = mLanguageManager.getLabel(getArguments().getString(REGION_TYPE));
            String purpose = mLanguageManager.getLabel(getArguments().getString(PURPOSE));

            if (getArguments().containsKey(METHOD_TYPE)) {
                txtFarmDetails.setText(region + "\n"
                        + mLanguageManager.getLabel(getArguments().getString(METHOD_TYPE))
                        + " , " + mLanguageManager.getLabel(getArguments().getString(CROP_TYPE))
                        + " , " + purpose);
            } else {
                txtFarmDetails.setText(region + "\n"
                        + mLanguageManager.getLabel(getArguments().getString(CROP_TYPE))
                        + " , " + purpose);
            }
        }

    }

    private void addPossibleVarieties() {
        final RealmList<VarietyModel> list;
        if (getArguments().containsKey(WATER_AVAILABILITY)) { // we have sent water availability ony when user selects Rabi as crop type
            list = RealmController.with().getPossibleVarieties(getArguments().getString(REGION_TYPE)
                    /*, getArguments().getString(METHOD_TYPE)*/
                    , getArguments().getString(CROP_TYPE)
                    , getArguments().getString(PURPOSE)
                    , getArguments().getString(WATER_AVAILABILITY));
        } else {
            list = RealmController.with().getPossibleVarieties(getArguments().getString(REGION_TYPE)
                    , getArguments().getString(CROP_TYPE)
                    , getArguments().getString(PURPOSE));
        }

        if (list.size() != 0) {
            LayoutInflater layoutInflater = getLayoutInflater();
            for (int i = 0; i < list.size(); i++) {
                View listItem = layoutInflater.inflate(R.layout.list_item_suggested, null);
                listItem.setTag(i);
                TextView varietyName = listItem.findViewById(R.id.txt_variety_name);
                TextView txtWaterAndYield = listItem.findViewById(R.id.txt_water_and_yield_potential);
                TextView txtMoreInfo = listItem.findViewById(R.id.txt_more_info_screen);
                txtMoreInfo.setText(mLanguageManager.getLabel(LabelConstant.MORE_INFO));
                varietyName.setText(mLanguageManager.getLabel(list.get(i).getVariety().trim()));
                txtWaterAndYield.setText(mLanguageManager.getLabel(LabelConstant.WATER_NEEDED) + ":" + mLanguageManager.getLabel(list.get(i).getWaterAvailability()) + "\n"
                        + mLanguageManager.getLabel(LabelConstant.YIELD_POTENTIAL) + ":" + list.get(i).getYieldPotential());


                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = (int) view.getTag();
                        launchFragment(list.get(position).getScreenId(), list.get(position).getVariety());
                    }
                });


                llContainerPossibleVarieties.addView(listItem);
            }
            txtLabelPossibleVarietiesSubText.setText(mLanguageManager.getLabel(LabelConstant.SUB_HEADER_SUITABLE_VARIETIES));
        } else {
            txtLabelPossibleVarietiesSubText.setText(mLanguageManager.getLabel(LabelConstant.SUB_HEADER_NO_SUITABLE_VARIETIES));
        }

        addOtherVarieties(list);


    }


    /**
     * here we have fetched all varieties from DB and from that we have removed possible varieties
     * so resulting remains other varieties.
     *
     * @param possibleList
     */
    private void addOtherVarieties(RealmList<VarietyModel> possibleList) {
        final RealmList<VarietyModel> list = RealmController.with().getAllVarieties();
        for (int i = 0; i < possibleList.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (possibleList.get(i).getVariety().equalsIgnoreCase(list.get(j).getVariety())) {
                    list.remove(j);
                }
            }
        }

        if (list.size() != 0) {
            LayoutInflater layoutInflater = getLayoutInflater();
            for (int i = 0; i < list.size(); i++) {
                View listItem = layoutInflater.inflate(R.layout.list_item_suggested, null);
                listItem.setTag(i);
                TextView txtMoreInfo = listItem.findViewById(R.id.txt_more_info_screen);
                TextView varietyName = listItem.findViewById(R.id.txt_variety_name);
                TextView txtWaterAndYield = listItem.findViewById(R.id.txt_water_and_yield_potential);
                txtMoreInfo.setText(mLanguageManager.getLabel(LabelConstant.MORE_INFO));
                varietyName.setText(mLanguageManager.getLabel(list.get(i).getVariety().trim()));
                txtWaterAndYield.setText(mLanguageManager.getLabel(LabelConstant.WATER_NEEDED) + ":" + mLanguageManager.getLabel(list.get(i).getWaterAvailability()) + "\n"
                        + mLanguageManager.getLabel(LabelConstant.YIELD_POTENTIAL) + ":" + list.get(i).getYieldPotential());

                listItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        final int position = (int) view.getTag();
                        String msg = "";
                        msg = getWarningMsg(list.get(position).getWaterAvailability(), list.get(position).getVariety());
                        AppUtility.showAlertDialog(getActivity(), mLanguageManager.getLabel(LabelConstant.ALERT), msg, mLanguageManager.getLabel(LabelConstant.YES), mLanguageManager.getLabel(LabelConstant.NO), new OnDialogButtonClickListener() {
                            @Override
                            public void onDialogButtonClickListner(int whichButton) {
                                switch (whichButton) {
                                    case Constants.DIALOG_ACTION_OK:

                                        launchFragment(list.get(position).getScreenId(), list.get(position).getVariety());
                                        break;

                                    case Constants.DIALOG_ACTION_CANCEL:

                                        break;

                                }
                            }
                        });
                    }
                });

                llContainerOtherVarieties.addView(listItem);
            }
        }

    }

    private String getWarningMsg(String requiredWater, String varietyType) {

        StringBuffer buffer = new StringBuffer();

        // we have to follow this order given by client to show multiple messages
//        1. Purpose
//        2. Region
//        3. Water availability
//        4. Season

        VarietySelection varietySelection = RealmController.with().getVarietySelectionSetting(varietyType);

        // 1.  warning for purpose

        if (!varietySelection.getListPurposeTypes().contains(getArguments().getString(PURPOSE))) {
            buffer.append(mLanguageManager.getLabel(LabelConstant.WARNING_MSG_MISMATCH_PURPOSE));
            buffer.append("\n\n");
        }

        // 2.  warning for region
        if (!varietySelection.getListRegionTypes().contains(getArguments().getString(REGION_TYPE))) {
            buffer.append(mLanguageManager.getLabel(LabelConstant.WARNING_MSG_MISMATCH_REGION));
            buffer.append("\n\n");
        }


        //3.  new logic for water availability

        if (!varietySelection.getListWaterAvailability().contains(getArguments().getString(WATER_AVAILABILITY))) {
            buffer.append(mLanguageManager.getLabel(LabelConstant.INDICATED_WATER_LEVEL_NOT_ENOUGH_VARIETY_SELECTION));
            buffer.append("\n\n");
        }

        // 4. crop type
        if (!varietySelection.getListCropTypes().contains(getArguments().getString(CROP_TYPE))) {
            if (getArguments().getString(CROP_TYPE).equalsIgnoreCase("CROP_TYPE_1")) {
                buffer.append(mLanguageManager.getLabel(LabelConstant.WARNING_MSG_MISMATCH_CROP_TYPE_RABI));
            } else {
                buffer.append(mLanguageManager.getLabel(LabelConstant.WARNING_MSG_MISMATCH_CROP_TYPE_KHARIF));
            }
        }

        return buffer.toString();
    }

    @OnClick(R2.id.txt_change_information)
    public void ChangeInformation() {
        //finish();
        ((VarietySelectionActivity) getActivity()).popUpFragment();
    }


    public void launchFragment(String screen_id, String variety) {
        if (!TextUtils.isEmpty(screen_id)) {
            Fragment fragment = ScreenManager.getFragmentForId(screen_id);
            Bundle bundle = fragment.getArguments();
            bundle.putString(VarietySelectionActivity.ARGS_VARIETY, variety);
            fragment.setArguments(bundle);
            if (fragment != null) {
                if (fragment instanceof DialogFragment) {
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.show(getFragmentManager(), dialogFragment.getClass().getName());
                } else {
                    //((VarietySelectionActivity) getActivity()).replaceFragment(R.id.variety_selection_container, fragment, true);
                    ((VarietySelectionActivity) getActivity()).replaceFragment(fragment);
                }
            } else {
                /// show default fragment when fragment  is null.
                showDefaultFragment();
            }
        } else {
            /// show default fragment when screen id is empty.
            showDefaultFragment();
        }
    }


    private void showDefaultFragment() {
        //((VarietySelectionActivity) getActivity()).replaceFragment(R.id.variety_selection_container, new DefaultFragment(), true);
        ((VarietySelectionActivity) getActivity()).replaceFragment(new DefaultFragment());
    }
}
