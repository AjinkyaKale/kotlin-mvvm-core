package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.util.LabelConstant;
import com.smartfarming.screenrender.util.Utility;


/**
 * Created by harshadbagul on 1/6/17.
 */

public class DefaultFragment extends BaseFragment {


    public static Fragment newInstance() {
        DefaultFragment defaultFragment = new DefaultFragment();
        return defaultFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_default, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(LabelConstant.DEFAULT_TITLE);

        // Track screen by name on Fire-base server
        Utility.trackScreenByClassName(getActivity(), this.getClass());

        return view;
    }


}
