package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.smartfarming.screenrender.model.TextModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;

import java.util.List;

/**
 * Created by harshadbagul on 2/6/17.
 */

public class PheromoneTrapQuestionFragment extends BaseFragment implements OnFragmentLoad {


    @BindView(R2.id.tv_pheromone_question_title)
    CustomFontTextView tv_pheromone_question_title;

    @BindView(R2.id.tv_pheromone_quetion_1)
    CustomFontTextView tv_pheromone_quetion_1;

    @BindView(R2.id.tv_pheromone_quetion_2)
    CustomFontTextView tv_pheromone_quetion_2;

    @BindView(R2.id.tv_pheromone_quetion_3)
    CustomFontTextView tv_pheromone_quetion_3;

    @BindView(R2.id.btn_pheromone_quetion_yes_1)
    Button btn_pheromone_quetion_yes_1;

    @BindView(R2.id.btn_pheromone_quetion_no_1)
    Button btn_pheromone_quetion_no_1;

    @BindView(R2.id.btn_pheromone_quetion_yes_2)
    Button btn_pheromone_quetion_yes_2;

    @BindView(R2.id.btn_pheromone_quetion_no_2)
    Button btn_pheromone_quetion_no_2;

    @BindView(R2.id.btn_pheromone_quetion_yes_3)
    Button btn_pheromone_quetion_yes_3;

    @BindView(R2.id.btn_pheromone_quetion_no_3)
    Button btn_pheromone_quetion_no_3;

    @BindView(R2.id.unselector_2)
    View unselector_2;
    @BindView(R2.id.unselector_3)
    View unselector_3;

    @BindView(R2.id.tvImageName)
    CustomFontTextView tvImageName;


    @BindView(R2.id.iv_pheromonetrap_trap)
    ImageView iv_pheromonetrap_trap;

    private String screen_id;
    private ScreenModel screenModel;


    public static PheromoneTrapQuestionFragment newInstance() {
        PheromoneTrapQuestionFragment fragment = new PheromoneTrapQuestionFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pheromone_trap_question, container, false);

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
        btn_pheromone_quetion_yes_1.setText(languageManager.getLabel("yes"));
        btn_pheromone_quetion_no_1.setText(languageManager.getLabel("no"));

        btn_pheromone_quetion_yes_2.setText(languageManager.getLabel("yes"));
        btn_pheromone_quetion_no_2.setText(languageManager.getLabel("no"));

        btn_pheromone_quetion_yes_3.setText(languageManager.getLabel("yes"));
        btn_pheromone_quetion_no_3.setText(languageManager.getLabel("no"));

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tv_pheromone_question_title, screenModel.getTextModels().get(0).getValue(), this);//

            List<TextModel> list = ScreenManager.getQuestionArray(screenModel);
            if (list.size() == 1) {
                AppUtility.renderHtmlInATextView(tv_pheromone_quetion_1, list.get(0).getValue(), this);
            } else if (list.size() == 2) {
                AppUtility.renderHtmlInATextView(tv_pheromone_quetion_1, list.get(0).getValue(), this);
                AppUtility.renderHtmlInATextView(tv_pheromone_quetion_2, list.get(1).getValue(), this);
            } else if (list.size() == 3) {
                AppUtility.renderHtmlInATextView(tv_pheromone_quetion_1, list.get(0).getValue(), this);
                AppUtility.renderHtmlInATextView(tv_pheromone_quetion_2, list.get(1).getValue(), this);
                AppUtility.renderHtmlInATextView(tv_pheromone_quetion_3, list.get(2).getValue(), this);
            }

            //Drawable drawable = FileManager.getImageDrawable(getActivity(),screenModel.getImageModels().get(0).getName());
            //Bitmap bitmap = FileManager.getImageBitmap(getActivity(),screenModel.getImageModels().get(0).getName(),Constants.SCREEN);
            //iv_pheromonetrap_trap.setImageBitmap(bitmap);

            String imagePath = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(0).getName());
            Glide.with(getActivity()).load(imagePath).into(iv_pheromonetrap_trap);

            String info = screenModel.getImageModels().get(0).getInfo();
            tvImageName.setText(info);
        }
    }


    @OnClick(R2.id.iv_pheromonetrap_trap)
    public void OnImageClick() {
        ImageDialog imageDialog = new ImageDialog(getActivity(), screenModel.getImageModels().get(0).getName());
        imageDialog.show();
    }


    @OnClick(R2.id.btn_pheromone_quetion_no_1)
    public void OnButtonNo_1_click() {
        String screen_id = null;
        try {
            screen_id = String.valueOf(screenModel.getTextModels().get(1).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(screen_id);
    }

    /**
     * 1. Hide question 1 "No" button
     * 2. Hide question 2 overlay view
     * 3. Enable question 2 button clicks
     */
    @OnClick(R2.id.btn_pheromone_quetion_yes_1)
    public void OnButtonYes_1_click() {

        hideView(R.id.btn_pheromone_quetion_no_1);
        hideView(R.id.unselector_2);
        setViewClickable(R.id.btn_pheromone_quetion_yes_2);
        setViewClickable(R.id.btn_pheromone_quetion_no_2);
    }


    @OnClick(R2.id.btn_pheromone_quetion_no_2)
    public void OnButtonNo_2_click() {
        String screen_id = null;
        try {
            screen_id = String.valueOf(screenModel.getTextModels().get(2).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(screen_id);
    }

    /**
     * 1. Hide question 2 "No" button
     * 2. Hide question 3 overlay view
     * 3. Enable question 3 button clicks
     */
    @OnClick(R2.id.btn_pheromone_quetion_yes_2)
    public void OnButtonYes_2_click() {
        hideView(R.id.btn_pheromone_quetion_no_2);
        hideView(R.id.unselector_3);
        setViewClickable(R.id.btn_pheromone_quetion_yes_3);
        setViewClickable(R.id.btn_pheromone_quetion_no_3);
    }


    @OnClick(R2.id.btn_pheromone_quetion_no_3)
    public void OnButtonNo_3_click() {
        String screen_id = null;
        try {
            screen_id = String.valueOf(screenModel.getTextModels().get(3).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(screen_id);
    }

    /**
     * 1. Show ETL reached screen
     */
    @OnClick(R2.id.btn_pheromone_quetion_yes_3)
    public void OnButtonYes_3_click() {
        hideView(R.id.btn_pheromone_quetion_no_3);
        String screen_id = null;
        try {
            screen_id = String.valueOf(screenModel.getTextModels().get(3).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(screen_id);
    }


    private void hideView(int resourceId) {
        getView().findViewById(resourceId).setVisibility(View.GONE);
    }

    private void setViewClickable(int resourceId) {
        getView().findViewById(resourceId).setClickable(true);
        //getView().findViewById(resourceId).setOnClickListener(this);
    }


}
