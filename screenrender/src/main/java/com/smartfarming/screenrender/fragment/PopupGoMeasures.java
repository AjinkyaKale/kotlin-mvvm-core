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

public class PopupGoMeasures extends BaseDialogFragment implements OnFragmentLoad, OnHtmlLinkClickListener {


    @BindView(R2.id.tvTitle)
    CustomFontTextView tvTitle;

    @BindView(R2.id.btnGoToMeasures)
    AppCompatButton btnGoToMeasures;


    private String screen_id;
    private ScreenModel screenModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.popup_go_measure, container, false);
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
    public void onHtmlLinkClickListener(String clickedText) {
        dismiss();
        String screen_id = Utility.isInteger(getActivity(), clickedText);
        if (!TextUtils.isEmpty(screen_id)) {
            launchFragment(screen_id);
        }
    }


    @Override
    public void setLanguageLabel() {
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        String label = screenModel.getButtonsModels().get(0).getLable();
        System.out.println("label : >>>>>>>>>>> " + label);
        btnGoToMeasures.setText(languageManager.getLabel(label));

//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tvTitle, screenModel.getTextModels().get(0).getValue(), this);
        }

        // tvTitle.setText(getResources().getString(R.string.go_measure));
    }


    @OnClick(R2.id.btnGoToMeasures)
    public void onGoMeasureClick() {
        dismiss();
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getButtonsModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }

}
