package com.smartfarming.screenrender.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.activity.BaseActivity;
import com.smartfarming.screenrender.activity.VarietySelectionActivity;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.smartfarming.screenrender.view.CustomViewPager;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

/**
 * Created by sopnil on 10/4/18.
 */

public class LaunchInfoFragment extends BaseFragment implements OnFragmentLoad/*, OnHtmlLinkClickListener*/ {

    @BindView(R2.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circleIndicator;
    @BindView(R2.id.tvDescription)
    TextView tvDescription;
    @BindView(R2.id.btn_start)
    Button btnStart;

    @BindView(R2.id.img_right)
    ImageView imgRight;

    @BindView(R2.id.img_left)
    ImageView imgLeft;

    public String from = "";

    public static final String FROM = "from";
    public static final String IRRIGATION = "Irrigation";
    public static final String SPACING_SCREEN = "spacing_density";
    public static final String FERTILIZATION = "Fertilizer";
    public static final String CHOOSE_VARIETY = "choose variety";
    public static final String IDENTIFICATION = "identification";
    public static final String ORGANIC = "organic";


    View fragmentView;
    private String screen_id;
    private ScreenModel screenModel;
    private LanguageManager mLanguageManager;

    public static Fragment newInstance(String from) {
        LaunchInfoFragment fragment = new LaunchInfoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_launch_info, container, false);
            ButterKnife.bind(this, fragmentView);

            try {
                getPreviousArguments();
                setScreenContent();
                setLanguageLabel();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Track screen by name on Fire-base server
            Utility.trackScreenByClassName(getActivity(), this.getClass());
        }

        return fragmentView;
    }


    @Override
    public void getPreviousArguments() {
        mLanguageManager = LanguageManager.getInstance(getActivity());
        if (getArguments() != null) {
            screen_id = getArguments().getString(Constants.SCREEN_ID_KEY);
            title_label = getArguments().getString(Constants.SCREEN_TITLE_KEY);
//            if(getArguments().getString(FROM)!=null) {
            from = getArguments().getString(FROM);
//            }
        }
    }

    @Override
    public void setLanguageLabel() {
//        Utility.trackScreenByScreenName(getActivity(),mLanguageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            // tvDescription.setText(screenModel.getTextModels().get(0).getValue());
            AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(0).getValue(), this);
            btnStart.setText(mLanguageManager.getLabel(screenModel.getButtonsModels().get(0).getLable()));
            String[] arrImages = ScreenManager.getImageNameArray(screenModel.getImageModels());
            Drawable[] images = FileManager.getImageDrawable(getActivity(), screenModel);
            setImageAdapter(screenModel.getImageModels());
        }
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewPager.setAdapter(imagePestPagerAdapter);
        circleIndicator.setViewPager(viewPager);

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItemofviewpager(+1), true);
            }
        });
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(getItemofviewpager(-1), true);
            }
        });
    }

    private int getItemofviewpager(int i) {
        return viewPager.getCurrentItem() + i;
    }


    @OnClick(R2.id.btn_start)
    public void launchScreen() {

        if (from.equalsIgnoreCase(IRRIGATION)) {
            ((BaseActivity) getActivity()).launchAppFragment(IRRIGATION);
        } else if (from.equalsIgnoreCase(SPACING_SCREEN)) {
            ((BaseActivity) getActivity()).launchAppFragment(SPACING_SCREEN);
        } else if (from.equalsIgnoreCase(FERTILIZATION)) {
            String cretification[] = RealmController.with().getLabelKeyArray(Constants.CERTIFICATION_TYPE);
            if (RealmController.with().getUser().getCertification().equalsIgnoreCase(cretification[1])) {
                ((BaseActivity) getActivity()).launchAppFragment(FERTILIZATION);
            } else {
                launchFragment(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.ORGANIC));
            }
        } else if (from.equalsIgnoreCase(CHOOSE_VARIETY)) {
            Intent intent = new Intent(getActivity(), VarietySelectionActivity.class);
            intent.putExtra(VarietySelectionActivity.NAVIGATION_FROM, VarietySelectionActivity.NAVIGATION_FROM_PRE_SOWING);
            intent.putExtra(VarietySelectionActivity.ARGS_REGION_PRE_FILLED, RealmController.with().getUser().getPositionRegion());
            intent.putExtra(VarietySelectionActivity.ARGS_PURPOSE_PRE_FILLED, RealmController.with().getUser().getPositionPurpose());
            getActivity().startActivity(intent);
        } else if (from.equalsIgnoreCase(IDENTIFICATION)) {
            if (Long.valueOf(RealmController.with().getUser().getSowingDate()) <= 0) {
                LanguageManager languageManager = LanguageManager.getInstance(getActivity());
                AppUtility.showAlertDialog(getActivity(), languageManager.getLabel(LabelConstant.ALERT_SOWING_DATE));
            } else {
                String stageId = ScreenManager.getStageForDAS(getActivity());
                launchFragment(stageId);
            }
        }

    }

//    @Override
//    public void onHtmlLinkClickListener(String clickedText) {
//        //launchFragment(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(clickedText));
//    }
}
