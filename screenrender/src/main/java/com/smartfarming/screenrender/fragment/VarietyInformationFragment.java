package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.activity.VarietySelectionActivity;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

/**
 * Created by sopnil on 7/4/18.
 */

public class VarietyInformationFragment extends com.smartfarming.screenrender.fragment.BaseFragment implements OnFragmentLoad {


    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.tvDescription)
    TextView tvDescription;

    @BindView(R2.id.btnTop)
    Button btnUse;

    @BindView(R2.id.btnBottom)
    Button btnOther;

    @BindView(R2.id.tvTitle)
    TextView tvTitle;

    private String screen_id;
    private ScreenModel screenModel;
    private String varietyKey;
    private LanguageManager languageManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_variety_information, container, false);

        ButterKnife.bind(this, view);
        languageManager = LanguageManager.getInstance(getActivity());
        try {
            getPreviousArguments();
            setLanguageLabel();
            setScreenContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Track screen by name on Fire-base server
        Utility.trackScreenByClassName(getActivity(), this.getClass());
        return view;
    }


    @Override
    public void getPreviousArguments() {
        if (getArguments() != null) {
            screen_id = getArguments().getString(Constants.SCREEN_ID_KEY);
            title_label = getArguments().getString(Constants.SCREEN_TITLE_KEY);
            varietyKey = getArguments().getString(VarietySelectionActivity.ARGS_VARIETY);
        }
    }

    @Override
    public void setLanguageLabel() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        btnUse.setText(languageManager.getLabel(LabelConstant.USER_THIS_VARIETY));
        btnOther.setText(languageManager.getLabel(LabelConstant.SHOW_OTHER_OPTION));
//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {

        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            //tvTitle.setText(screenModel.getTextModels().get(0).getValue());
            AppUtility.renderHtmlInATextView(tvTitle, screenModel.getTextModels().get(0).getValue(), this);
            AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(1).getValue(), this);
            setImageAdapter(screenModel.getImageModels());
        }
    }

    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewpager.setAdapter(imagePestPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);
    }

    @OnClick(R2.id.btnTop)
    public void btnUseVariety() {
        ((VarietySelectionActivity) getActivity()).sendVarietyResult(varietyKey);
    }


    @OnClick(R2.id.btnBottom)
    public void btnShowOtherOption() {
        ((VarietySelectionActivity) getActivity()).popUpFragment();
    }
}
