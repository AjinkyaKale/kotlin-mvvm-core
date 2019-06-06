package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.dialog.ImageDialog;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.smartfarming.screenrender.view.CustomFontTextView;


public class PestPageBollwormsFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvPestName)
    CustomFontTextView tvPestName;
    @BindView(R2.id.tvImportance)
    TextView tvImportance;
    @BindView(R2.id.tvMajorOrModerate)
    TextView tvMajorOrModerate;
    @BindView(R2.id.tvFileText)
    CustomFontTextView tvFileText;

    @BindView(R2.id.tvMonitoringAdvice)
    TextView tvMonitoringAdvice;
    @BindView(R2.id.tvPestSpecificAdvice)
    CustomFontTextView tvPestSpecificAdvice;

    @BindView(R2.id.ivImage)
    ImageView ivImage;

    @BindView(R2.id.btnLeft)
    AppCompatButton btnLeft;

    @BindView(R2.id.btnRight)
    AppCompatButton btnRight;

    @BindView(R2.id.tvPestQuestion)
    CustomFontTextView tvPestQuestion;


    private LanguageManager languageManager;
    private ScreenModel screenModel;
    private String screen_id;

    public static Fragment newInstance() {
        PestPageBollwormsFragment fragment = new PestPageBollwormsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pest_page_bollworms, container, false);
        ButterKnife.bind(this, view);

        try {
            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        tvImportance.setText(languageManager.getLabel("pest_importance"));
        tvMonitoringAdvice.setText(languageManager.getLabel("pest_monitoring_advice"));

        try {
            btnLeft.setText(languageManager.getLabel(screenModel.getTextModels().get(3).getButtonModels().get(0).getLable()));
            btnRight.setText(languageManager.getLabel(screenModel.getTextModels().get(3).getButtonModels().get(1).getLable()));

//            Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            languageManager = LanguageManager.getInstance(getActivity());
            screenModel = ScreenManager.getScreenObject(screen_id);

            String majorModerate = Pref.getInstance(getActivity()).getMajorModerate();
            if (majorModerate.equalsIgnoreCase(ScreenManager.MAJOR_KEY)) {
                tvMajorOrModerate.setText(languageManager.getLabel("pest_major"));
                tvMajorOrModerate.setTextColor(getResources().getColor(R.color.colorRed));
            } else {
                tvMajorOrModerate.setText(languageManager.getLabel("pest_moderate"));
                tvMajorOrModerate.setTextColor(getResources().getColor(R.color.orange));
            }

            AppUtility.renderHtmlInATextView(tvPestName, screenModel.getTextModels().get(0).getValue(), this);
            AppUtility.renderHtmlInATextView(tvFileText, screenModel.getTextModels().get(1).getValue(), this);
            AppUtility.renderHtmlInATextView(tvPestSpecificAdvice, screenModel.getTextModels().get(2).getValue(), this);
            AppUtility.renderHtmlInATextView(tvPestQuestion, screenModel.getTextModels().get(3).getValue(), this);

            // Bitmap bitmap = FileManager.getImageBitmap(getActivity(), screenModel.getImageModels().get(0).getName(), Constants.SCREEN);
            // ivImage.setImageBitmap(bitmap);
            // ivImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

            String imagePath = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(0).getName());
            Glide.with(getActivity()).load(imagePath).into(ivImage);

        }
    }


    @OnClick(R2.id.ivImage)
    public void onCLickImage() {
        ImageDialog imageDialog = new ImageDialog(getActivity(), screenModel.getImageModels().get(0).getName());
        imageDialog.show();
    }

    @OnClick(R2.id.btnLeft)
    public void OnLeftButtonClick() {

        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getTextModels().get(3).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }


    @OnClick(R2.id.btnRight)
    public void OnRightButtonClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getTextModels().get(3).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }


}
