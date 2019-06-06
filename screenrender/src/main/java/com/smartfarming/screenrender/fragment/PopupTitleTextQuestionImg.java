package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;

public class PopupTitleTextQuestionImg extends BaseDialogFragment implements OnFragmentLoad {

    @BindView(R2.id.tv_dialog_title)
    CustomFontTextView tv_dialog_title;

    @BindView(R2.id.tv_dialog_description)
    CustomFontTextView tv_dialog_description;

    @BindView(R2.id.tv_dialog_question)
    CustomFontTextView tv_dialog_question;

    @BindView(R2.id.ivBtn1)
    ImageView ivBtn1;
    @BindView(R2.id.tvBtn1)
    TextView tvBtn1;

    @BindView(R2.id.ivBtn2)
    ImageView ivBtn2;
    @BindView(R2.id.tvBtn2)
    TextView tvBtn2;

    private String screen_id;
    private ScreenModel screenModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.popup_title_text_question_img, container, false);
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
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tv_dialog_title, screenModel.getTextModels().get(0).getValue(), this);
            AppUtility.renderHtmlInATextView(tv_dialog_description, screenModel.getTextModels().get(1).getValue(), this);
            AppUtility.renderHtmlInATextView(tv_dialog_question, screenModel.getTextModels().get(2).getValue(), this);

            // ivBtn1.setImageBitmap(FileManager.getImageBitmap(getActivity(),screenModel.getImageModels().get(0).getName(),Constants.DIALOG));
            // ivBtn2.setImageBitmap(FileManager.getImageBitmap(getActivity(),screenModel.getImageModels().get(1).getName(),Constants.DIALOG));

            String imagePath1 = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(0).getName());
            Glide.with(getActivity()).load(imagePath1).into(ivBtn1);

            String imagePath2 = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(1).getName());
            Glide.with(getActivity()).load(imagePath2).into(ivBtn2);

            tvBtn1.setText(screenModel.getImageModels().get(0).getInfo());
            tvBtn2.setText(screenModel.getImageModels().get(1).getInfo());

        }
    }


    @OnClick(R2.id.ivBtn1)
    public void onButton1Click() {
        dismiss();
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getImageModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);

    }

    @OnClick(R2.id.ivBtn2)
    public void onButton2Click() {
        dismiss();
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getImageModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }
}
