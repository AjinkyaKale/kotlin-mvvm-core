package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.MeasureFragmentPagerAdapter;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;


public class MeasuresFragment extends BaseFragment implements OnFragmentLoad {


    @BindView(R2.id.view_pager_white_fly)
    ViewPager viewPagerWhiteFly;


    private MeasureFragmentPagerAdapter pagerAdapter;
    private String screen_id;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] SCREENS_IDs;


    public static MeasuresFragment newInstance() {
        MeasuresFragment fragment = new MeasuresFragment();
        return fragment;
    }


    /**
     * inflate and return the view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_measures, container, false);
        ButterKnife.bind(this, fragmentView);

        getPreviousArguments();
        setLanguageLabel();

        initViews(fragmentView);

        // Track screen by name on Fire-base server
        Utility.trackScreenByClassName(getActivity(), this.getClass());

        return fragmentView;
    }


    /**
     * initialize view
     *
     * @param fragmentView
     */
    private void initViews(View fragmentView) {

        viewPager = (ViewPager) fragmentView.findViewById(R.id.view_pager_white_fly);
        setViewPager(viewPager);

        tabLayout = (TabLayout) fragmentView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * set view pager
     *
     * @param viewPager
     */
    private void setViewPager(ViewPager viewPager) {
        pagerAdapter = new MeasureFragmentPagerAdapter(getChildFragmentManager(), getActivity(), SCREENS_IDs);
        viewPager.setAdapter(pagerAdapter);
        setCurrentTab(viewPager);
        pagerAdapter.notifyDataSetChanged();
    }


    /**
     * set current selected tab with pos
     * and screen id to each page (fragment)
     *
     * @param viewPager
     */
    private void setCurrentTab(ViewPager viewPager) {
        ScreenModel screenModel = ScreenManager.getScreenObject(screen_id);
        String screen_type = screenModel.getScreenType();

        switch (screen_type) {
            case "tab_general_info_pest":
                viewPager.setCurrentItem(0);
                SCREENS_IDs[0] = screen_id;
                SCREENS_IDs[1] = screenModel.getTabs().get(0).getLinkTo();
                SCREENS_IDs[2] = screenModel.getTabs().get(1).getLinkTo();
                break;

            case "tab_biological_pest":
                viewPager.setCurrentItem(1);
                SCREENS_IDs[0] = screenModel.getTabs().get(0).getLinkTo();
                SCREENS_IDs[1] = screen_id;
                SCREENS_IDs[2] = screenModel.getTabs().get(1).getLinkTo();
                break;

            case "tab_chemical_control_pest":
                viewPager.setCurrentItem(2);
                SCREENS_IDs[0] = screenModel.getTabs().get(0).getLinkTo();
                SCREENS_IDs[1] = screenModel.getTabs().get(1).getLinkTo();
                SCREENS_IDs[2] = screen_id;
                break;

            default:
                viewPager.setCurrentItem(0);
                break;
        }

    }


    @Override
    public void getPreviousArguments() {
        if (getArguments() != null) {
            SCREENS_IDs = new String[3];
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

    }


}
