package com.smartfarming.screenrender.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.adapter.ImagePestPagerAdapter;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.model.ImageModel;
import com.smartfarming.screenrender.model.PheromoneTrapDateModel;
import com.smartfarming.screenrender.model.ScreenModel;
import com.smartfarming.screenrender.util.*;
import com.smartfarming.screenrender.view.CustomFontTextView;
import com.viewpagerindicator.CirclePageIndicator;
import io.realm.Realm;
import io.realm.RealmList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class PheromoneTrapFragment extends BaseFragment implements OnFragmentLoad, DatePickerDialog.OnDateSetListener {

    @BindView(R2.id.view_pager)
    ViewPager viewpager;

    @BindView(R2.id.circle_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R2.id.tvPheromoneTrap)
    CustomFontTextView tvPheromoneTrap;

    @BindView(R2.id.tvPestName)
    CustomFontTextView tvPestName;

    @BindView(R2.id.tvDescription)
    CustomFontTextView tvDescription;

    @BindView(R2.id.tvPheromoneTrapPlaced)
    CustomFontTextView tvPheromoneTrapPlaced;

    @BindView(R2.id.tvLuresPlaced)
    CustomFontTextView tvLuresPlaced;

    @BindView(R2.id.btnInsertTrap)
    Button btnInsertTrap;

   /* @BindView(R2.id.ivInsertLures)
    ImageView ivInsertLures;*/

    private String PHEROMONE_TRAP_TEXT = "";
    private String LURES_TEXT = "";
    private String LURES_TEXT_2 = "";
    private final String TRAP_MODE = "PheromoneTrap";
    // private final String LURES_MODE = "Lures";
    private String selectedMode = "";
    private String screen_id;
    private ScreenModel screenModel;
    private String selectedLuresDate = "";
    private String selectedTrapDate = "";


    public static Fragment newInstance() {
        PheromoneTrapFragment fragment = new PheromoneTrapFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pheromone_trap, container, false);

        ButterKnife.bind(this, view);

        getPreviousArguments();
        setLanguageLabel();
        setScreenContent();

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
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel(title_label));
            PHEROMONE_TRAP_TEXT = languageManager.getLabel("pheromone_trap_placed");
            tvPheromoneTrapPlaced.setText(PHEROMONE_TRAP_TEXT);
            btnInsertTrap.setText(languageManager.getLabel("insert"));
            LURES_TEXT = languageManager.getLabel("lures_placed_1");
            LURES_TEXT_2 = languageManager.getLabel("lures_placed_2");

//            Utility.trackScreenByScreenName(getActivity(),languageManager.getLabel(title_label));

            //tvLuresPlaced.setText(LURES_TEXT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R2.id.btnInsertTrap)
    public void OnClickInsertDate() {
        selectedMode = TRAP_MODE;
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment(this, null, null, getActivity());
        datePickerDialogFragment.show(getActivity().getFragmentManager(), DatePickerDialogFragment.DATE_PICKER);
    }


   /* @OnClick(R2.id.ivInsertLures)
    public void OnClickInsertLuresDate(){
        selectedMode = LURES_MODE;
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment(this,getMinDate(),null,getActivity());
        datePickerDialogFragment.show(getActivity().getFragmentManager(), DatePickerDialogFragment.DATE_PICKER);
    }*/


    /**
     * get min date for pheromone traps
     *
     * @return
     */
    @NonNull
    private Calendar getMinDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -14);
        return c;
    }


    @Override
    public void setScreenContent() {
        if (screen_id != null) {

            try {
                screenModel = ScreenManager.getScreenObject(screen_id);

                setSelectedDatesForTraps();

                AppUtility.renderHtmlInATextView(tvPheromoneTrap, screenModel.getTextModels().get(0).getValue(), this);
                AppUtility.renderHtmlInATextView(tvPestName, screenModel.getTextModels().get(1).getValue(), this);
                AppUtility.renderHtmlInATextView(tvDescription, screenModel.getTextModels().get(2).getValue(), this);

                setImageAdapter(screenModel.getImageModels());


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * set view pager adapter for screen images.
     *
     * @param images
     */
    private void setImageAdapter(RealmList<ImageModel> images) {
        ImagePestPagerAdapter imagePestPagerAdapter = new ImagePestPagerAdapter(getActivity(), images);
        viewpager.setAdapter(imagePestPagerAdapter);
        circlePageIndicator.setViewPager(viewpager);
    }


    /**
     * show already selected dates for the Pheromone traps using screen id
     */
    private void setSelectedDatesForTraps() {
        PheromoneTrapDateModel pheromoneTrapDateModel;
        pheromoneTrapDateModel = RealmController.getInstance().getPheromoneTrapDate(screen_id);
        if (pheromoneTrapDateModel != null) {
            selectedTrapDate = pheromoneTrapDateModel.getTrapDate();
            selectedLuresDate = pheromoneTrapDateModel.getLuresDate();

            if (!TextUtils.isEmpty(selectedTrapDate) && !TextUtils.isEmpty(selectedLuresDate)) {

                SimpleDateFormat originalFormat = new SimpleDateFormat(Constants.DATE_FORMAT_2);
                SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MMM-yyyy");
                Date dateTrap = null;
                Date dateLures = null;
                try {
                    dateTrap = originalFormat.parse(selectedTrapDate);
                    dateLures = originalFormat.parse(selectedLuresDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String luresDateShow = targetFormat.format(dateLures);
                tvLuresPlaced.setText(LURES_TEXT + " " + luresDateShow + " , " + LURES_TEXT_2);

                String trapDateShow = targetFormat.format(dateTrap);
                tvPheromoneTrapPlaced.setText(PHEROMONE_TRAP_TEXT + " " + trapDateShow);
            }
        }
    }


    /**
     * set tarp date in PheromoneTrapDateModel model and save model to db.
     *
     * @param selectedLuresDate
     * @param selectedTrapDate
     */
    private void saveDateToDB(String selectedLuresDate, String selectedTrapDate) {
        PheromoneTrapDateModel pheromoneTrapDateModel;

        Realm realm = RealmController.getInstance().getRealm();
        realm.beginTransaction();
        pheromoneTrapDateModel = RealmController.getInstance().getPheromoneTrapDate(screen_id);

        if (pheromoneTrapDateModel != null) {
            //pheromoneTrapDateModel.setScreenId(screen_id);
            pheromoneTrapDateModel.setLuresDate(selectedLuresDate);
            pheromoneTrapDateModel.setTrapDate(selectedTrapDate);
        } else {
            pheromoneTrapDateModel = new PheromoneTrapDateModel();
            pheromoneTrapDateModel.setScreenId(screen_id);
            pheromoneTrapDateModel.setLuresDate(selectedLuresDate);
            pheromoneTrapDateModel.setTrapDate(selectedTrapDate);
        }

        realm.commitTransaction();

        ScreenManager.savePheromoneTrapDate(pheromoneTrapDateModel);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar selectedDateCalender = Calendar.getInstance();
        selectedDateCalender.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormatter1 = new SimpleDateFormat(Constants.DATE_FORMAT_2);
        String date = dateFormatter1.format(selectedDateCalender.getTime());

        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd-MMM-yyyy");
        String dateToShow = dateFormatter2.format(selectedDateCalender.getTime());

        // set trap date in preference according to screen id , to re-calculate rotational advices

        HashMap<String, String> lureDates = Pref.getInstance(getActivity()).getLureDates();
        if (lureDates.containsKey(screen_id)) {
            SimpleDateFormat lureDateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT);
            String lureDate = lureDateFormatter.format(selectedDateCalender.getTime());
            lureDates.put(screen_id, lureDate);
            Pref.getInstance(getActivity()).setLureDates(lureDates);
        }
        //  logic ends here for date of re-calculate rotational advices


        switch (selectedMode) {

            case TRAP_MODE:

                tvPheromoneTrapPlaced.setText(PHEROMONE_TRAP_TEXT + " " + dateToShow);
                String lures_date = AppUtility.getAddedDateWithFormat(date, Constants.DATE_FORMAT_2, 14);

                Date date1 = null;
                try {
                    date1 = dateFormatter1.parse(lures_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String luresDateToShow = dateFormatter2.format(date1);

                tvLuresPlaced.setText(LURES_TEXT + " " + luresDateToShow + " , " + LURES_TEXT_2);
                selectedLuresDate = lures_date;
                selectedTrapDate = date;

                break;

            /*case LURES_MODE:
                tvLuresPlaced.setText(LURES_TEXT+" "+dateToShow);
                selectedLuresDate = date;
                break;*/

            default:
                break;

        }

        saveDateToDB(selectedLuresDate, selectedTrapDate);
    }


}
