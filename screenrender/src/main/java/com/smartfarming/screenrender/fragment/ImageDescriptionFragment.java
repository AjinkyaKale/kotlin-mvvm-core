package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.smartfarming.screenrender.model.TextModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

import java.util.List;

public class ImageDescriptionFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.tvQuestion)
    CustomFontTextView tvQuestion;

    @BindView(R2.id.btnNo)
    AppCompatButton btnNo;

    @BindView(R2.id.btnYes)
    AppCompatButton btnYes;

    @BindView(R2.id.btnChange)
    AppCompatButton btnChange;

    @BindView(R2.id.btnLayout)
    LinearLayout btnLayout;

    @BindView(R2.id.img_right)
    ImageView imgRight;

    @BindView(R2.id.img_left)
    ImageView imgLeft;

    private String screen_id;
    private ScreenModel screenModel;

    public static Fragment newInstance() {
        ImageDescriptionFragment fragment = new ImageDescriptionFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_description, container, false);

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
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
            btnNo.setText(languageManager.getLabel("basal_dose_question_1_btn_no"));
            btnYes.setText(languageManager.getLabel("basal_dose_question_1_btn_yes"));
            btnChange.setText(languageManager.getLabel("change"));

//            Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            try {
                screenModel = ScreenManager.getScreenObject(screen_id);
                AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(0).getValue(), this);
                setImageAdapter(screenModel.getImageModels());

                setQuestionAnswerLayout();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        if (images.size() != 0) {
            ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
            viewpager.setAdapter(imagePestPagerAdapter);
            circlePageIndicator.setViewPager(viewpager);

            imgRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewpager.setCurrentItem(getItemofviewpager(+1), true);
                }
            });
            imgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewpager.setCurrentItem(getItemofviewpager(-1), true);
                }
            });
        } else {
            imgRight.setVisibility(View.GONE);
            imgLeft.setVisibility(View.GONE);
        }
    }

    private int getItemofviewpager(int i) {
        return viewpager.getCurrentItem() + i;
    }

    private void setQuestionAnswerLayout() {
        List<TextModel> list = screenModel.getTextModels();
        String question = null;
        for (int i = 0; i < list.size(); i++) {
            String type = list.get(i).getType();
            if (type.equalsIgnoreCase("question")) {
                question = list.get(i).getValue();
                break;
            }
        }

        if (!TextUtils.isEmpty(question)) {
            tvQuestion.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.VISIBLE);

            AppUtility.renderHtmlInATextView(tvQuestion, question, this);
            setPreviousSelectedButtons();
        } else {
            tvQuestion.setVisibility(View.GONE);
            btnLayout.setVisibility(View.GONE);
        }
    }

    private void setPreviousSelectedButtons() {
        String selectedAnswer = ScreenManager.getSelectedFeedback(screen_id);
        if (!TextUtils.isEmpty(selectedAnswer)) {
            switch (selectedAnswer) {
                case ScreenManager.NO_BUTTON_KEY:
                    btnNo.setVisibility(View.VISIBLE);
                    btnYes.setVisibility(View.GONE);
                    btnChange.setVisibility(View.VISIBLE);
                    break;
                case ScreenManager.YES_BUTTON_KEY:
                    btnNo.setVisibility(View.GONE);
                    btnYes.setVisibility(View.VISIBLE);
                    btnChange.setVisibility(View.VISIBLE);
                    break;
                case ScreenManager.CHANGE_BUTTON_KEY:
                    btnNo.setVisibility(View.VISIBLE);
                    btnYes.setVisibility(View.VISIBLE);
                    btnChange.setVisibility(View.GONE);
                    break;
                default:
                    btnNo.setVisibility(View.VISIBLE);
                    btnYes.setVisibility(View.VISIBLE);
                    btnChange.setVisibility(View.GONE);
                    break;
            }
        } else {
            btnNo.setVisibility(View.VISIBLE);
            btnYes.setVisibility(View.VISIBLE);
            btnChange.setVisibility(View.GONE);
        }
    }


    @OnClick(R2.id.btnNo)
    public void OnNoButtonClick() {
        ScreenManager.setFeedbackForScreenId(screen_id, ScreenManager.NO_BUTTON_KEY);
        setPreviousSelectedButtons();
    }

    @OnClick(R2.id.btnYes)
    public void OnYesButtonClick() {
        ScreenManager.setFeedbackForScreenId(screen_id, ScreenManager.YES_BUTTON_KEY);
        setPreviousSelectedButtons();
    }

    @OnClick(R2.id.btnChange)
    public void OnChangeButtonClick() {
        ScreenManager.setFeedbackForScreenId(screen_id, ScreenManager.CHANGE_BUTTON_KEY);
        setPreviousSelectedButtons();
    }

}
