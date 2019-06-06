package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.smartfarming.screenrender.view.CustomFontTextView;


public class PopSeeLarvae extends BaseDialogFragment implements OnHtmlLinkClickListener, OnFragmentLoad {

    @BindView(R2.id.btnLeft)
    AppCompatButton btnLeft;

    @BindView(R2.id.btnRight)
    AppCompatButton btnRight;


    @BindView(R2.id.tv_dialog_question)
    CustomFontTextView tv_dialog_question;

    private String screen_id;
    private ScreenModel screenModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.popup_see_larvae, container, false);

        ButterKnife.bind(this, view);


        getPreviousArguments();
        setScreenContent();
        setLanguageLabel();

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
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        try {
            btnLeft.setText(languageManager.getLabel(screenModel.getTextModels().get(0).getButtonModels().get(0).getLable()));
            btnRight.setText(languageManager.getLabel(screenModel.getTextModels().get(0).getButtonModels().get(1).getLable()));

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));

    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            try {
                AppUtility.renderHtmlInATextView(tv_dialog_question, screenModel.getTextModels().get(0).getValue(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onHtmlLinkClickListener(String clickedText) {
        dismiss();
        String screen_id = Utility.isInteger(getActivity(), clickedText);
        if (!TextUtils.isEmpty(screen_id)) {
            launchFragment(screen_id);
        }
    }


    @OnClick(R2.id.btnLeft)
    public void OnLeftButtonClick() {
        dismiss();
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }


    @OnClick(R2.id.btnRight)
    public void OnRightButtonClick() {
        dismiss();
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }


}
