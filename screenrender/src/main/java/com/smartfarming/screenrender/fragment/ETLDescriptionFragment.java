package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;


public class ETLDescriptionFragment extends BaseFragment implements OnFragmentLoad {


    @BindView(R2.id.btnLeft)
    AppCompatButton btnLeft;

    @BindView(R2.id.btnRight)
    AppCompatButton btnRight;

    @BindView(R2.id.tv_etl_reached_title)
    CustomFontTextView tv_etl_reached_title;

    @BindView(R2.id.ivMore_Info)
    ImageView ivMore_Info;

    @BindView(R2.id.tv_etl_reached_description)
    CustomFontTextView tv_etl_reached_description;

    /*@BindView(R2.id.tv_etl_reached_pest_name)
    CustomFontTextView tv_etl_reached_pest_name;
*/
    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    private String screen_id;
    private ScreenModel screenModel;


    public static ETLDescriptionFragment newInstance() {
        ETLDescriptionFragment fragment = new ETLDescriptionFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_etl_description, container, false);

        ButterKnife.bind(this, view);
        try {
            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Track screen by name on Fire-base server
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
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        btnLeft.setText(languageManager.getLabel(screenModel.getTextModels().get(1).getButtonModels().get(0).getLable()));
        btnRight.setText(languageManager.getLabel(screenModel.getTextModels().get(1).getButtonModels().get(1).getLable()));

//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tv_etl_reached_title, screenModel.getTextModels().get(0).getValue(), this);
            String description = screenModel.getTextModels().get(1).getValue();
            AppUtility.renderHtmlInATextView(tv_etl_reached_description, description, this);
            setImageAdapter(screenModel.getImageModels());
        }
    }


    @OnClick(R2.id.btnLeft)
    public void OnLeftButtonClick() {
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getTextModels().get(1).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }

    @OnClick(R2.id.btnRight)
    public void OnRightButtonClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getTextModels().get(1).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }


    @OnClick(R2.id.ivMore_Info)
    public void OnMoreInfoClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getButtonsModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewpager.setAdapter(imagePestPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);
    }


}
