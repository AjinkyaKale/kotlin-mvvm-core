package com.smartfarming.screenrender.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.LabelConstant;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomViewPager;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

/**
 * Created by sopnil on 26/3/18.
 */

public class VarietyDetailFragment extends BaseFragment implements OnFragmentLoad {

    View fragmentView;
    @BindView(R2.id.tvVarietyName)
    TextView tvVarietyName;
    @BindView(R2.id.tvDescription)
    TextView tvDescription;
    @BindView(R2.id.btnUseThisVariety)
    Button btnUseThisVariety;
    @BindView(R2.id.btnShowOtherOption)
    Button btnShowOtherOption;
    @BindView(R2.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circleIndicator;


    private String screen_id;
    private ScreenModel screenModel;

    public static Fragment newInstance() {
        VarietyDetailFragment fragment = new VarietyDetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_variety_detail, container, false);
        ButterKnife.bind(this, fragmentView);

        try {
            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Track screen by name on Fire-base server
        Utility.trackScreenByClassName(getActivity(), this.getClass());

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        tvVarietyName.setText(languageManager.getLabel(LabelConstant.VARIETY_SELECTION));
        //tvDescription.setText("Description");
        btnUseThisVariety.setText(languageManager.getLabel(LabelConstant.I_USE_THIS_VARIETY));
        btnShowOtherOption.setText(languageManager.getLabel(LabelConstant.SHOW_OTHER_OPTIONS));

//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            tvDescription.setText(screenModel.getTextModels().get(0).getValue());
            String[] arrImages = ScreenManager.getImageNameArray(screenModel.getImageModels());
            Drawable[] images = FileManager.getImageDrawable(getActivity(), screenModel);
            setImageAdapter(screenModel.getImageModels());
        }
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewPager.setAdapter(imagePestPagerAdapter);
        circleIndicator.setViewPager(viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // null.unbind();
        //null.unbind();
    }
}
