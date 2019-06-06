package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.activity.BaseActivity;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

public class IdentificationTipsFragment extends BaseFragment implements OnHtmlLinkClickListener, OnFragmentLoad {


    @BindView(R2.id.btn)
    AppCompatButton btn;

    @BindView(R2.id.tvDescription)
    TextView tvDescription;

    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    private String screen_id;
    private ScreenModel screenModel;

    public static Fragment newInstance() {
        IdentificationTipsFragment identificationFragment = new IdentificationTipsFragment();
        return identificationFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_identification_tips, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getPreviousArguments();
            setLanguageLabel();
            setScreenContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(LabelConstant.IDENTIFICATION_TIPS_TITLE));
        btn.setText(languageManager.getLabel(LabelConstant.START_IDENTIFICATION));
        AppUtility.renderHtmlInATextView(tvDescription, languageManager.getLabel(LabelConstant.IDENTIFICATION_TIPS), this);
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            setImageAdapter(screenModel.getImageModels());
        }
    }


    @OnClick(R2.id.btn)
    public void OnButtonClick() {

        /*if (RealmController.with().getUser().getSowingDate().equalsIgnoreCase("0")){
            LanguageManager languageManager = LanguageManager.getInstance(getActivity());
            AppUtility.showAlertDialog(getActivity() , languageManager.getLabel(LabelConstant.ALERT_SOWING_DATE));
        }*/

        if (Long.valueOf(RealmController.with().getUser().getSowingDate()) <= 0) {
            LanguageManager languageManager = LanguageManager.getInstance(getActivity());
            AppUtility.showAlertDialog(getActivity(), languageManager.getLabel(LabelConstant.ALERT_SOWING_DATE));
        } else {
            String stageId = ScreenManager.getStageForDAS(getActivity());
            launchFragment(stageId);
        }
    }


    public void launchFragment(String screen_id) {
        if (!TextUtils.isEmpty(screen_id)) {
            Fragment fragment = ScreenManager.getFragmentForId(screen_id);
            if (fragment != null) {
                if (fragment instanceof DialogFragment) {
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.show(getFragmentManager(), dialogFragment.getClass().getName());
                } else {
                    ((BaseActivity) getActivity()).replaceFragment(fragment);
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
        ((BaseActivity) getActivity()).replaceFragment(new DefaultFragment());
    }

    private void setImageAdapter(RealmList<ImageModel> images) {
        /*Drawable[] arrImages = {getResources().getDrawable(R2.drawable.ic_identification_tips_1), getResources().getDrawable(R2.drawable.ic_identification_tips_2)};
        ImageDescriptionPagerAdapter imageDescriptionPagerAdapter = new ImageDescriptionPagerAdapter(getActivity(), arrImages);
        viewpager.setAdapter(imageDescriptionPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);*/
        ImagePestPagerAdapter imageDescriptionPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewpager.setAdapter(imageDescriptionPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);
    }


    @Override
    public void onHtmlLinkClickListener(String clickedText) {
        String screen_id = Utility.isInteger(getActivity(), clickedText);
        if (!TextUtils.isEmpty(screen_id)) {
            launchFragment(screen_id);
        }
    }


}
