package com.smartfarming.screenrender.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.activity.BaseActivity;
import com.smartfarming.screenrender.adapter.TitleAdapter;
import com.smartfarming.screenrender.database.RealmController;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.model.ActionAdviceModel;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnFragmentLoad;
import com.smartfarming.screenrender.util.Utility;
import com.smartfarming.screenrender.view.CustomFontTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TitleListFragment extends BaseFragment implements OnFragmentLoad {

    @BindView(R2.id.tvActionAdviceTitle)
    CustomFontTextView tvActionAdviceTitle;

    @BindView(R2.id.listview_action_advice)
    ListView listview_action_advice;

    // private List<ActionAdviceModel> listActionAdvices ;
    private String screen_id;

    public static Fragment newInstance() {
        TitleListFragment fragment = new TitleListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title_list, container, false);

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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(languageManager.getLabel("dashboard_action_advice"));
        tvActionAdviceTitle.setText(languageManager.getLabel("action_advice_title"));

//        Utility.trackScreenByScreenName(getActivity(), languageManager.getLabel(title_label));
    }

    @Override
    public void setScreenContent() {
        List<String> list = new ArrayList<>();
        List<ActionAdviceModel> listActionAdvices = RealmController.getInstance().getActionAdviceModels();

        List<ActionAdviceModel> orderedActionAdvices = prepareOrderForAdvices(listActionAdvices);
        setListener(orderedActionAdvices);

        for (int i = 0; i < orderedActionAdvices.size(); i++) {
            list.add(orderedActionAdvices.get(i).getTitle());
        }

        TitleAdapter adapter = new TitleAdapter(getActivity(), list);
        listview_action_advice.setAdapter(adapter);

    }


    private List<ActionAdviceModel> prepareOrderForAdvices(List<ActionAdviceModel> listActionAdvices) {

        List<ActionAdviceModel> advicesDate = new ArrayList<>();
        List<ActionAdviceModel> advicesOther = new ArrayList<>();

        for (int i = 0; i < listActionAdvices.size(); i++) {
            String type = listActionAdvices.get(i).getType();
            if (type.equals("date")) {
                advicesDate.add(listActionAdvices.get(i));
            } else {
                advicesOther.add(listActionAdvices.get(i));
            }
        }

        Collections.sort(advicesDate, new Comparator<ActionAdviceModel>() {
            public int compare(ActionAdviceModel m1, ActionAdviceModel m2) {
                Date d1 = null, d2 = null;
                SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_2, Locale.getDefault());
                try {
                    d1 = formatter.parse(m1.getDate());
                    d2 = formatter.parse(m2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return d2.compareTo(d1);
            }
        });
        advicesDate.addAll(advicesOther);

        return advicesDate;
    }


    /**
     * set listener to the listview
     */
    private void setListener(final List<ActionAdviceModel> listActionAdvices) {
        listview_action_advice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putString(Constants.ADVICE_KEY, listActionAdvices.get(position).getId());

                ActionAdviceDetailFragment actionAdviceDetailFragment = new ActionAdviceDetailFragment();
                actionAdviceDetailFragment.setArguments(bundle);
                ((BaseActivity) getActivity()).replaceFragment(actionAdviceDetailFragment);

            }
        });
    }


}
