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
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.FieldLayoutAdapter;
import com.smartfarming.screenrender.dialog.ImageDialog;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.FieldModel;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.model.TextModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FieldLayoutFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.listField)
    ListView listField;

    @BindView(R2.id.ivField)
    ImageView ivField;


    private String screen_id;
    private ScreenModel screenModel;
    private View rootView;


    public static Fragment newInstance() {
        FieldLayoutFragment fragment = new FieldLayoutFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_field_layout, container, false);

            ButterKnife.bind(this, rootView);

            getPreviousArguments();
            setScreenContent();
            setLanguageLabel();

            //Track screen by name on Fire-base server
            Utility.trackScreenByClassName(getActivity(), this.getClass());
        }

        return rootView;
    }

    @Override
    public void getPreviousArguments() {
        if (getArguments() != null) {
            try {
                screen_id = getArguments().getString(Constants.SCREEN_ID_KEY);
                title_label = getArguments().getString(Constants.SCREEN_TITLE_KEY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setLanguageLabel() {
        LanguageManager languageManager = LanguageManager.getInstance(getActivity());
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));

//            Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R2.id.ivField)
    public void OnClickImage() {
        ImageDialog imageDialog = new ImageDialog(getActivity(), screenModel.getImageModels().get(0).getName());
        imageDialog.show();
    }

    @Override
    public void setScreenContent() {
        if (screen_id != null) {
            try {
                screenModel = ScreenManager.getScreenObject(screen_id);

                // set image
                // ivField.setImageBitmap(FileManager.getImageBitmap(getActivity(), screenModel.getImageModels().get(0).getName(),Constants.SCREEN));

                String imagePath = FileManager.getImagePath(getActivity(), screenModel.getImageModels().get(0).getName());
                Glide.with(getActivity()).load(imagePath).into(ivField);

                List<FieldModel> fieldModels = prepareListForField();

                FieldLayoutAdapter fieldLayoutAdapter = new FieldLayoutAdapter(getActivity(),
                        R.layout.row_field_layout, fieldModels, FieldLayoutFragment.this);
                listField.setAdapter(fieldLayoutAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<FieldModel> prepareListForField() {

        List<FieldModel> fieldModels = new ArrayList<>();

        List<TextModel> listTextModel = screenModel.getTextModels();

        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> images = new ArrayList<>();

        for (int i = 0; i < listTextModel.size(); i++) {
            String type = listTextModel.get(i).getType();
            if (type.equalsIgnoreCase("title")) {
                String title = listTextModel.get(i).getValue();
                titles.add(title);
            }

            if (type.equalsIgnoreCase("desc")) {
                String description = listTextModel.get(i).getValue();
                descriptions.add(description);
            }
        }

        List<ImageModel> imageModels = screenModel.getImageModels();
        for (int i = 1; i < imageModels.size(); i++) {
            String image_name = imageModels.get(i).getName();
            images.add(image_name);
        }


        for (int i = 0; i < titles.size(); i++) {
            FieldModel fieldModel = new FieldModel();
            fieldModel.setTitle(titles.get(i));
            fieldModel.setDescription(descriptions.get(i));

            fieldModels.add(fieldModel);
        }

        for (int i = 0; i < images.size(); i++) {

            FieldModel model = fieldModels.get(i);
            model.setIcon(images.get(i));
            fieldModels.set(i, model);
        }


       /* for (int i = 0; i < fieldModels.size() ; i++) {
            FieldModel model = fieldModels.get(i);
            System.out.println("-----------------------");
            System.out.println("title : "+model.getTitle());
            System.out.println("description : "+model.getDescription());
            System.out.println("icon : "+model.getIcon());
        }
*/
        return fieldModels;
    }


}
