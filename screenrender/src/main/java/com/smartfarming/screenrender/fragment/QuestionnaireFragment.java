package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;

/**
 * Created by harshadbagul on 31/5/17.
 */

public class QuestionnaireFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvQuestion)
    CustomFontTextView tvQuestion;

    @BindView(R2.id.btnLeft)
    AppCompatButton btnLeft;

    @BindView(R2.id.btnRight)
    AppCompatButton btnRight;

    @BindView(R2.id.btnCenter)
    AppCompatButton btnCenter;

    @BindView(R2.id.iv_photo_identification_left_arrow)
    ImageView iv_photo_identification_left_arrow;

    @BindView(R2.id.iv_photo_identification_right_arrow)
    ImageView iv_photo_identification_right_arrow;

    @BindView(R2.id.tvImageName)
    CustomFontTextView tvImageName;

    @BindView(R2.id.ivImage)
    ImageView ivImage;


    private String screen_id;
    private ScreenModel screenModel;


    public static QuestionnaireFragment newInstance() {
        QuestionnaireFragment fragment = new QuestionnaireFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_questionnaire, container, false);

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        try {
            btnLeft.setText(languageManager.getLabel(screenModel.getTextModels().get(0).getButtonModels().get(0).getLable()));
            btnRight.setText(languageManager.getLabel(screenModel.getTextModels().get(0).getButtonModels().get(2).getLable()));
            btnCenter.setText(languageManager.getLabel(screenModel.getTextModels().get(0).getButtonModels().get(1).getLable()));

//            Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tvQuestion, screenModel.getTextModels().get(0).getValue(), this);
            tvImageName.setText(screenModel.getImageModels().get(0).getInfo());

            String imagePath = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(0).getName());
            // Bitmap bitmap = FileManager.getImageBitmap(getActivity(),screenModel.getImageModels().get(0).getName(),Constants.SCREEN);
            Glide.with(this).load(imagePath).into(ivImage);
            //ivImage.setImageBitmap(bitmap);
            //ivImage.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
            //File file =  FileManager.getImagePath(getActivity(),screenModel.getImageModels().get(0).getName());
        }
    }


    @OnClick(R2.id.ivImage)
    public void OnImageClick() {
        ImageDialog imageDialog = new ImageDialog(getActivity(), screenModel.getImageModels().get(0).getName());
        imageDialog.show();
    }


    @OnClick(R2.id.iv_photo_identification_left_arrow)
    public void OnLeftArrowClick() {
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getButtonsModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }


    @OnClick(R2.id.iv_photo_identification_right_arrow)
    public void OnRightArrowClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getButtonsModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }

    @OnClick(R2.id.btnLeft)
    public void OnLeftButtonClick() {
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }

    @OnClick(R2.id.btnCenter)
    public void OnCenterButtonClick() {
        String center_btn_id = null;
        try {
            center_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(center_btn_id);
    }

    @OnClick(R2.id.btnRight)
    public void OnRightButtonClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getTextModels().get(0).getButtonModels().get(2).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }


}
