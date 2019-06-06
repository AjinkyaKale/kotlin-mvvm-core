package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.model.ActionAdviceModel;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.OnHtmlLinkClickListener;
import com.smartfarming.screenrender.view.CustomFontTextView;
import io.realm.RealmResults;


public class ActionAdviceDetailFragment extends BaseFragment implements OnFragmentLoad, OnHtmlLinkClickListener {

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    private String mActionAdviceKey;

    public static Fragment newInstance() {
        ActionAdviceDetailFragment fragment = new ActionAdviceDetailFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        ButterKnife.bind(this, view);

        try {
            getPreviousArguments();
            setLanguageLabel();
            setScreenContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    @Override
    public void getPreviousArguments() {
        if (getArguments() != null) {
            mActionAdviceKey = getArguments().getString(Constants.ADVICE_KEY);
        }
    }

    @Override
    public void setLanguageLabel() {
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel("dashboard_action_advice"));
    }

    @Override
    public void setScreenContent() {
        if (mActionAdviceKey != null) {
            //  LanguageManager languageManager = LanguageManager.getInstance(getActivity());
            //  String description = languageManager.getLabel(mActionAdviceKey);
            RealmResults<ActionAdviceModel> actionAdviceModelList = RealmController.getInstance().getActionAdviceModels();
            ActionAdviceModel actionAdviceModel = actionAdviceModelList.where().equalTo("id", mActionAdviceKey).findFirst();

            if (actionAdviceModel != null) {
                AppUtility.renderHtmlInATextView(tvDescription, actionAdviceModel.getAdvice(), this);
            }

        }
    }

//    @Override
//    public void onHtmlLinkClickListener(String clickedText) {
//        String screen_id = Utility.isInteger(getActivity(), clickedText);
//        if (!TextUtils.isEmpty(screen_id)) {
//            launchFragment(screen_id);
//        }
//    }

}
