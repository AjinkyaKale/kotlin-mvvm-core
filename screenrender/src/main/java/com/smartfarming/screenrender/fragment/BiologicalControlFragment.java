package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;

public class BiologicalControlFragment extends BaseFragment implements OnFragmentLoad {

   /* @BindView(R2.id.relfarmerApprove)
    RelativeLayout relFarmerApprove;

    @BindView(R2.id.relTestedByresearch)
    RelativeLayout relTestedByresearch;

    @BindView(R2.id.txt_description_biological_control)
    TextView txtApprovedMeasures;

    @BindView(R2.id.txt_testedby_research)
    TextView txtTestedbyResearch;


    @BindView(R2.id.img_right_one)
    ImageView imgRightOne;

    @BindView(R2.id.img_right_two)
    ImageView imgrightTwo;


    @BindView(R2.id.txt_title_1)
    TextView txt_title_1;

    @BindView(R2.id.txt_title_2)
    TextView txt_title_2;*/

    @BindView(R2.id.tvDescription)
    TextView mTextViewDescription;


    private String screen_id;
    private ScreenModel screenModel;


    public static BiologicalControlFragment newInstance() {
        Bundle bundle = new Bundle();
        BiologicalControlFragment fragment = new BiologicalControlFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmetView = inflater.inflate(R.layout.fragment_biological_info, container, false);
        ButterKnife.bind(this, fragmetView);

        try {
            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Track screen by name on Fire-base server
        Utility.trackScreenByClassName(getActivity(), this.getClass());

        return fragmetView;
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
        title_label = screenModel.getTitle();
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {

        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(mTextViewDescription, screenModel.getTextModels().get(0).getValue(), this);
//            AppUtility.renderHtmlInATextView(txtApprovedMeasures, screenModel.getTextModels().get(1).getValue(), this);
//
//            AppUtility.renderHtmlInATextView(txt_title_2, screenModel.getTextModels().get(2).getValue(), this);
//            AppUtility.renderHtmlInATextView(txtTestedbyResearch, screenModel.getTextModels().get(3).getValue(), this);

          /*  AppUtility.renderHtmlInATextView(txt_title_3, screenModel.getTextModels().get(4).getValue(), this);
            AppUtility.renderHtmlInATextView(txtSuggestedNotTested, screenModel.getTextModels().get(5).getValue(), this);*/
        }
    }

   /* @OnClick(R2.id.txt_title_1)
    public void OnClickFarmerText1() {
        farmerApproved();
    }

    @OnClick(R2.id.txt_title_2)
    public void OnClickTestedByText2() {
        testedByResearch();
    }


    @OnClick(R2.id.relfarmerApprove)
    public void OnClickFarmerApprove() {
        farmerApproved();
    }

    @OnClick(R2.id.relTestedByresearch)
    public void OnClickTestedByresearch() {
        testedByResearch();
    }

    private void testedByResearch() {
        if (txtTestedbyResearch.getVisibility() == View.GONE) {
            txtTestedbyResearch.setVisibility(View.VISIBLE);
            txtApprovedMeasures.setVisibility(View.GONE);
            imgrightTwo.setImageResource(R.drawable.ic_arrow_up);
            imgRightOne.setImageResource(R.drawable.ic_next_arrow);
        } else {
            txtTestedbyResearch.setVisibility(View.GONE);
            imgrightTwo.setImageResource(R.drawable.ic_next_arrow);
        }
    }


    private void farmerApproved() {
        if (txtApprovedMeasures.getVisibility() == View.GONE) {
            txtApprovedMeasures.setVisibility(View.VISIBLE);
            txtTestedbyResearch.setVisibility(View.GONE);
            imgRightOne.setImageResource(R.drawable.ic_arrow_up);
            imgrightTwo.setImageResource(R.drawable.ic_next_arrow);
        } else {
            txtApprovedMeasures.setVisibility(View.GONE);
            imgRightOne.setImageResource(R.drawable.ic_next_arrow);
            imgrightTwo.setImageResource(R.drawable.ic_next_arrow);
        }
    }*/

}
