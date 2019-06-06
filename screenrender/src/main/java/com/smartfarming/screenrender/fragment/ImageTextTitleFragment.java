package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
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

import java.util.ArrayList;
import java.util.List;


public class ImageTextTitleFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.llTrapNames)
    LinearLayout llTrapNames;

    @BindView(R2.id.img_right)
    ImageView imgRight;

    @BindView(R2.id.img_left)
    ImageView imgLeft;

    private String screen_id;
    private ScreenModel screenModel;
    private List<TextModel> listPestNames;


    public static Fragment newInstance() {
        ImageTextTitleFragment fragment = new ImageTextTitleFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_text_titles, container, false);

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
            listPestNames = new ArrayList<>();
            llTrapNames.removeAllViews();

            screenModel = ScreenManager.getScreenObject(screen_id);
            AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(0).getValue(), this);
            setImageAdapter(screenModel.getImageModels());

            listPestNames = ScreenManager.getTitleArray(screenModel);
            for (int i = 0; i < listPestNames.size(); i++) {
                llTrapNames.addView(trapView(i));
            }


        }
    }


    private CustomFontTextView trapView(int position) {

        final CustomFontTextView textView = new CustomFontTextView(getActivity());
        //textView.setText(listPestNames.get(position).getValue());
        AppUtility.renderHtmlInATextView(textView, listPestNames.get(position).getValue(), null);
        textView.setTag(listPestNames.get(position).getLinkTo());
        textView.setPadding(8, 16, 8, 16);
        textView.setTextSize(16f);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String screen_id = textView.getTag().toString();//Utility.isInteger(getActivity(),textView.getTag().toString());
                if (!TextUtils.isEmpty(screen_id)) {
                    launchFragment(screen_id);
                }
            }
        });

        return textView;
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

    private void setListener() {
        /*listview_pest_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String screen_id = String.valueOf(listPestNames.get(position).getLinkTo());
                launchFragment(screen_id);
            }
        });*/
    }


}
