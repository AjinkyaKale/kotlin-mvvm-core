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
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.listeners.OnDateSelectListener;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

/**
 * Created by sopnil on 12/4/18.
 */

public class TitleImageTextInsertPheromoneTrap extends com.smartfarming.screenrender.fragment.BaseFragment implements OnFragmentLoad, OnDateSelectListener {


    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.tvDescription_one)
    TextView tvDescriptionOne;

    @BindView(R2.id.tvDescription_two)
    TextView tvDescriptionTwo;

    @BindView(R2.id.tvTitle)
    TextView tvTitle;

    @BindView(R2.id.tv_date_description)
    TextView tvDateLabel;

    @BindView(R2.id.btn_change_date)
    Button btnChangeDate;

    private String screen_id;
    private ScreenModel screenModel;
    private LanguageManager languageManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title_image_text_insert_pheromone_trap, container, false);

        ButterKnife.bind(this, view);

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
        }
    }

    @Override
    public void setLanguageLabel() {
        languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        tvDateLabel.setText(languageManager.getLabel(LabelConstant.DATE_OF_LURE_REPLACEMENT) + Pref.getInstance(getActivity()).getLureReplacementDate());
        String lureReplacementDate = Pref.getInstance(getActivity()).getLureReplacementDate();
        if ("".equalsIgnoreCase(lureReplacementDate)) {
            btnChangeDate.setText(languageManager.getLabel(LabelConstant.SET_DATE));
        } else {
            btnChangeDate.setText(languageManager.getLabel(LabelConstant.CHANGE_DATE));
            tvDateLabel.setText(languageManager.getLabel(LabelConstant.DATE_OF_LURE_REPLACEMENT) + ":" + Pref.getInstance(getActivity()).getLureReplacementDate());
        }

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tvTitle, screenModel.getTextModels().get(0).getValue(), this);
            AppUtility.renderHtmlInATextView(tvDescriptionOne, screenModel.getTextModels().get(1).getValue(), this);
            AppUtility.renderHtmlInATextView(tvDescriptionTwo, screenModel.getTextModels().get(2).getValue(), this);
            setImageAdapter(screenModel.getImageModels());
        }
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewpager.setAdapter(imagePestPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);
    }

    @OnClick(R2.id.btn_change_date)
    public void setLureSetUpDate() {
        AppUtility.showDatePickerDialog(getActivity(), Constants.DATE_FORMAT, 0, this);
    }

    @Override
    public void onDateSelectListener(String date, int tag) {
        tvDateLabel.setText(languageManager.getLabel(LabelConstant.DATE_OF_LURE_REPLACEMENT) + ":" + date);
        Pref.getInstance(getActivity()).setLureReplacementDate(date);
    }
}
