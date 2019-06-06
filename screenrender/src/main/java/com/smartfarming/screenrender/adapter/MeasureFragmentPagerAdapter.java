package com.smartfarming.screenrender.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.smartfarming.screenrender.fragment.BiologicalControlFragment;
import com.smartfarming.screenrender.fragment.ChemicalControlFragment;
import com.smartfarming.screenrender.fragment.GeneralInfoFragment;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.LabelConstant;

public class MeasureFragmentPagerAdapter extends FragmentStatePagerAdapter {


    int noOfTabs = 3;
    private Context context;
    private LanguageManager languageManager;
    private String[] SCREEN_IDS;

    /**
     * parametrized constructor
     *
     * @param fm
     * @param context
     * @param screen_ids
     */
    public MeasureFragmentPagerAdapter(FragmentManager fm, Context context, String[] screen_ids) {
        super(fm);
        this.context = context;
        this.SCREEN_IDS = screen_ids;
        languageManager = LanguageManager.getInstance(context);
    }

    /**
     * get instance of fragment according to position
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();


        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new GeneralInfoFragment();
                bundle.putString(Constants.SCREEN_ID_KEY, SCREEN_IDS[0]);
                break;

            case 1:
                fragment = new BiologicalControlFragment();
                bundle.putString(Constants.SCREEN_ID_KEY, SCREEN_IDS[1]);
                break;

            case 2:
                fragment = new ChemicalControlFragment();
                bundle.putString(Constants.SCREEN_ID_KEY, SCREEN_IDS[2]);
                break;
            default:
                break;
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * return no of tabs count for tab pager
     *
     * @return
     */
    @Override
    public int getCount() {
        return noOfTabs;
    }


    /**
     * get page title according to page view
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {

        String pageTitle = "";
        switch (position) {
            case 0:
                pageTitle = languageManager.getLabel(LabelConstant.TAB_GENERAL_INFO_PEST);
                break;

            case 1:
                pageTitle = languageManager.getLabel(LabelConstant.TAB_BIOLOGICAL_PEST);
                break;

            case 2:
                pageTitle = languageManager.getLabel(LabelConstant.TAB_CHEMICAL_CONTROL_PEST);
                break;

        }
        return pageTitle;
    }
}
