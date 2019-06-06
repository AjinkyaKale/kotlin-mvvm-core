package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.SubStagesAdapter;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.model.TextModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;

import java.util.ArrayList;
import java.util.List;


public class SubStagesStageWiseGapFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.img_substage_stage_wise_gap)
    ImageView img_substage_stage_wise_gap;

    @BindView(R2.id.listview_sub_stages)
    ListView listview_sub_stages;


    private ScreenModel screenModel;
    private List<TextModel> listSubStages = null;
    private String screen_id;


    public static Fragment newInstance() {
        SubStagesStageWiseGapFragment fragment = new SubStagesStageWiseGapFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_substages_stage_wise_gap, container, false);
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
            listSubStages = new ArrayList<>();
            screenModel = ScreenManager.getScreenObject(screen_id);
            // img_substage_stage_wise_gap.setImageBitmap(FileManager.getImageBitmap(getActivity(),screenModel.getImageModels().get(0).getName(),Constants.SCREEN));

            String imagePath = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(0).getName());
            Glide.with(getActivity()).load(imagePath).into(img_substage_stage_wise_gap);

            listSubStages.addAll(screenModel.getTextModels());
        }

        SubStagesAdapter subStagesAdapter = new SubStagesAdapter(getActivity(), listSubStages, SubStagesStageWiseGapFragment.this);
        listview_sub_stages.setAdapter(subStagesAdapter);

    }


}
