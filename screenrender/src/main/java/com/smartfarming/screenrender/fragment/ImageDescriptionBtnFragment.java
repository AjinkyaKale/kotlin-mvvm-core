package com.smartfarming.screenrender.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;


public class ImageDescriptionBtnFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.btn)
    AppCompatButton btn;

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.img_right)
    ImageView imgRight;

    @BindView(R2.id.img_left)
    ImageView imgLeft;


    private String screen_id;
    private ScreenModel screenModel;


    public static Fragment newInstance() {
        ImageDescriptionBtnFragment imageDescriptionBtnFragment = new ImageDescriptionBtnFragment();
        return imageDescriptionBtnFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_description_btn, container, false);
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
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
        btn.setText(languageManager.getLabel(screenModel.getButtonsModels().get(0).getLable()));
        // TODO: 5/3/18 add tracker
        //Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
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


    @OnClick(R2.id.btn)
    public void OnButtonClick() {
        String btn_Click_id = null;
        try {
            btn_Click_id = String.valueOf(screenModel.getButtonsModels().get(0).getLinkTo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        launchFragment(btn_Click_id);
    }


    private void setImageAdapter(RealmList<ImageModel> images) {
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
    }

    private int getItemofviewpager(int i) {
        return viewpager.getCurrentItem() + i;
    }

}
