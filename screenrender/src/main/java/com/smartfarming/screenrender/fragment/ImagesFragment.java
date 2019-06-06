package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.RealmList;


public class ImagesFragment extends BaseFragment implements OnFragmentLoad {

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
        ImagesFragment fragment = new ImagesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        ButterKnife.bind(this, view);

        try {
            getPreviousArguments();
            setLanguageLabel();
            setScreenContent();
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

//        Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            screenModel = ScreenManager.getScreenObject(screen_id);
            setImageAdapter(screenModel.getImageModels());
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


}
