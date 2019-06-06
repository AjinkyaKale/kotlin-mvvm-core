package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;

public class ImageTextQueYesNoFragment extends BaseFragment implements OnFragmentLoad {


    @BindView(R2.id.view_pager)
    ViewPager view_pager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circle_indicator;

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    @BindView(R2.id.tvQuestion)
    CustomFontTextView tvQuestion;

    @BindView(R2.id.btnLeft)
    AppCompatButton btnLeft;

    @BindView(R2.id.btnRight)
    AppCompatButton btnRight;

    @BindView(R2.id.img_right)
    ImageView imgRight;

    @BindView(R2.id.img_left)
    ImageView imgLeft;


    private String screen_id;
    private ScreenModel screenModel;


    public static ImageTextQueYesNoFragment newInstance() {
        ImageTextQueYesNoFragment fragment = new ImageTextQueYesNoFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_text_que_yes_no, container, false);

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
        btnLeft.setText(languageManager.getLabel(screenModel.getTextModels().get(1).getButtonModels().get(0).getLable()));
        btnRight.setText(languageManager.getLabel(screenModel.getTextModels().get(1).getButtonModels().get(1).getLable()));

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(0).getValue(), this);
            AppUtility.renderHtmlInATextView(tvQuestion, screenModel.getTextModels().get(1).getValue(), this);
            setImageAdapter(screenModel.getImageModels());
        }
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
        if (images.size() != 0) {
            ImagePestPagerAdapter imageDescriptionPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
            view_pager.setAdapter(imageDescriptionPagerAdapter);
            circle_indicator.setViewPager(view_pager);

            imgRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_pager.setCurrentItem(getItemofviewpager(+1), true);
                }
            });
            imgLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view_pager.setCurrentItem(getItemofviewpager(-1), true);
                }
            });
        } else {
            imgRight.setVisibility(View.GONE);
            imgLeft.setVisibility(View.GONE);
        }

    }

    private int getItemofviewpager(int i) {
        return view_pager.getCurrentItem() + i;
    }


    @OnClick(R2.id.btnLeft)
    public void OnLeftButtonClick() {
        String left_btn_id = null;
        try {
            left_btn_id = String.valueOf(screenModel.getTextModels().get(1).getButtonModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(left_btn_id);
    }


    @OnClick(R2.id.btnRight)
    public void OnRightButtonClick() {
        String right_btn_id = null;
        try {
            right_btn_id = String.valueOf(screenModel.getTextModels().get(1).getButtonModels().get(1).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(right_btn_id);
    }

}
