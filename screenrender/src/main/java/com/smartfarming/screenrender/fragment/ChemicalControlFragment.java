package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.smartfarming.screenrender.view.CustomFontTextView;
import io.realm.RealmList;


public class ChemicalControlFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    @BindView(R2.id.ll_pest_names)
    LinearLayout ll_pest_names;

    @BindView(R2.id.btnHowToApply)
    Button btnHowToApply;

    private String screen_id;
    private ScreenModel screenModel;

    public static Fragment newInstance() {
        ChemicalControlFragment fragment = new ChemicalControlFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_chemical_control, container, false);

        ButterKnife.bind(this, view);

        try {
            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Track screen by name on Fire-base server
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
        title_label = screenModel.getTitle();
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        String label = screenModel.getButtonsModels().get(0).getLable();
        System.out.println("label : " + label);
        btnHowToApply.setText(languageManager.getLabel(label));

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);

            // for description
            AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(0).getValue(), this);

            // for list of pest
            RealmList<TextModel> list = screenModel.getTextModels();
            setPestView(list);
        }

    }


    private void setPestView(RealmList<TextModel> list) {


        for (int i = 1; i < list.size(); i++) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pest_name, null);
            final CustomFontTextView textView = (CustomFontTextView) view.findViewById(R.id.txt_title);

            AppUtility.renderHtmlInATextView(textView, list.get(i).getValue(), this);
            textView.setTag(list.get(i).getLinkTo());
            textView.setPadding(8, 16, 8, 16);
            textView.setTextSize(16f);

            if (textView.getParent() != null)
                ((ViewGroup) textView.getParent()).removeView(textView);
            ll_pest_names.addView(textView);

           /* textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String screen_id = AppUtility.isInteger(getActivity(),textView.getTag().toString());
                    if (!TextUtils.isEmpty(screen_id)){
                        launchFragment(screen_id);
                    }
                }
            });*/
        }


    }


    @OnClick(R2.id.btnHowToApply)
    public void btnHowToApply() {
        String screen_id = null;
        try {
            screen_id = String.valueOf(screenModel.getButtonsModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(screen_id);
    }


}
