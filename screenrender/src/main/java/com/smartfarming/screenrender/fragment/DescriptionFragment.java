package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.model.TextModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;

import java.util.List;

public class DescriptionFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvDescription)
    TextView tvDescription;

    @BindView(R2.id.tvQuestion)
    TextView tvQuestion;

    @BindView(R2.id.btnNo)
    Button btnNo;

    @BindView(R2.id.btnYes)
    Button btnYes;

    @BindView(R2.id.btnChange)
    Button btnChange;

    @BindView(R2.id.btnLayout)
    LinearLayout btnLayout;


    private String screen_id;
    private ScreenModel screenModel;
    private View rootView;


    public static Fragment newInstance() {
        DescriptionFragment fragment = new DescriptionFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_description, container, false);

            ButterKnife.bind(this, rootView);

            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();

            Utility.trackScreenByClassName(getActivity(), this.getClass());
        }

        return rootView;
    }


    @Override
    public void getPreviousArguments() {
        if (getArguments() != null) {
            try {
                screen_id = getArguments().getString(Constants.SCREEN_ID_KEY);
                title_label = getArguments().getString(Constants.SCREEN_TITLE_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

//            Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
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

                setQuestionAnswerLayout();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
