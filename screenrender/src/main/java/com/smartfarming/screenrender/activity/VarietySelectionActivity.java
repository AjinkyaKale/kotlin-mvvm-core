package com.smartfarming.screenrender.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.R2;
import com.smartfarming.screenrender.fragment.VarietyQuestionnaireFragment;
import com.smartfarming.screenrender.listeners.LaunchAppActivity;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.util.LabelConstant;
import com.smartfarming.screenrender.util.Utility;

/**
 * Created by sopnil on 27/3/18.
 */

public class VarietySelectionActivity extends BaseActivity {

    public static final String ARGS_VARIETY = "VARIETY";
    public static final String ARGS_REGION_PRE_FILLED = "region";
    public static final String ARGS_PURPOSE_PRE_FILLED = "purpose";
    public static final String ARGS_SEASON_PRE_FILLED = "season";
    public static String NAVIGATION_FROM = "navigation_from";
    public static String NAVIGATION_FROM_PRE_SOWING = "navigation_from_pre_sowing";
    public static final String EDIT_MODE = "edit mode";
    public static final String REGISTER_MODE = "register mode";
    private String mNavigateFrom;
    public static final String MODULE_NAME = "module_name";
    public static final String MODULE_USER_PROFILE_ACTIVITY = "user_profile_activity";


    @BindView(R2.id.layout_toolbar)
    Toolbar mToolbar;
    LanguageManager mLanguageManager;
    public static final int RESULT_CODE = 1;

    @Override
    public void launchAppFragment(String type) {

    }

    @Override
    public void launchAppFragmentViaDailyAdvice(String type) {

    }

   /* @Override
    public void launchAppActivity(String type) {

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_variety_selection);
        ButterKnife.bind(this);
        mLanguageManager = LanguageManager.getInstance(this);
        setToolbar(mLanguageManager.getLabel(LabelConstant.VARIETY_SELECTION_TITLE_TOOLBAR));
        //replaceFragment(R.id.variety_selection_container, new VarietyQuestionnaireFragment(), true);
        setFragmentFrameId(R.id.variety_selection_container);

        VarietyQuestionnaireFragment fragment = new VarietyQuestionnaireFragment();
        Bundle bundle = new Bundle();
        if (getIntent() != null) {
            if (getIntent().hasExtra(VarietySelectionActivity.ARGS_REGION_PRE_FILLED)) {
                bundle.putInt(VarietySelectionActivity.ARGS_REGION_PRE_FILLED, getIntent().getIntExtra(VarietySelectionActivity.ARGS_REGION_PRE_FILLED, 0));
            }
            if (getIntent().hasExtra(VarietySelectionActivity.ARGS_PURPOSE_PRE_FILLED)) {
                bundle.putInt(VarietySelectionActivity.ARGS_PURPOSE_PRE_FILLED, getIntent().getIntExtra(VarietySelectionActivity.ARGS_PURPOSE_PRE_FILLED, 0));
            }
            if (getIntent().hasExtra(VarietySelectionActivity.ARGS_SEASON_PRE_FILLED)) {
                bundle.putString(VarietySelectionActivity.ARGS_SEASON_PRE_FILLED, getIntent().getStringExtra(VarietySelectionActivity.ARGS_SEASON_PRE_FILLED));
            }
            if (getIntent().hasExtra(VarietySelectionActivity.NAVIGATION_FROM)) {
                mNavigateFrom = getIntent().getStringExtra(VarietySelectionActivity.NAVIGATION_FROM);
            }
        }
        fragment.setArguments(bundle);
        replaceFragment(fragment);

        // Track screen by name on Fire-base server
        Utility.trackScreenByClassName(this, this.getClass());
    }

    public void setToolbar(String title) {
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitleTextColor(Color.WHITE);
    }


    /**
     * Creat toolbar menus from menu.xml
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigateFrom == null || !mNavigateFrom.equalsIgnoreCase(REGISTER_MODE)) {
            // inflating menu from res/menu
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_with_home, menu);
        }
        return true;

    }


    public void sendVarietyResult(String variety) {

        /*if (mNavigateFrom.equalsIgnoreCase(EDIT_MODE)) {
            Intent intent = new Intent();
            intent.putExtra(ARGS_VARIETY, variety);
            setResult(RESULT_CODE, intent);
            finish();
        }else if (mNavigateFrom.equalsIgnoreCase(NAVIGATION_FROM_PRE_SOWING)){
            //Intent intent = new Intent(VarietySelectionActivity.this , "com.smartfarming.potato.activity.UserProfileActivit");
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_LAUNCH_APP_ACTIVITY);
            intent.putExtra(MODULE_NAME , MODULE_USER_PROFILE_ACTIVITY);
            sendBroadcast(intent);
        }*/

        if (mNavigateFrom.equalsIgnoreCase(NAVIGATION_FROM_PRE_SOWING)) {
            /*Intent intent = new Intent();
            intent.setAction(Constants.ACTION_LAUNCH_APP_ACTIVITY);
            intent.putExtra(MODULE_NAME , MODULE_USER_PROFILE_ACTIVITY);
            sendBroadcast(intent);
            finish();*/
            LaunchAppActivity.getInstance(null).setVariety(variety);
            LaunchAppActivity.getInstance(null).getLaunchAppModule().launchActivity(VarietySelectionActivity.MODULE_USER_PROFILE_ACTIVITY);
        } else {
            Intent intent = new Intent();
            intent.putExtra(ARGS_VARIETY, variety);
            setResult(RESULT_CODE, intent);
            finish();
        }

        /*Intent intent = new Intent();
        intent.putExtra(ARGS_VARIETY, variety);
        setResult(RESULT_CODE, intent);
        finish();*/
    }


    /**
     * menu click
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        } else if (i == R.id.menu_item_home) {
            try {
                Intent myIntent = new Intent(this, Class.forName("com.smartfarming.potato.activity.DashBoardActivity"));
                startActivity(myIntent);
                ActivityCompat.finishAffinity(this);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToolbarTitle(String title) {
        mToolbar.setTitle(title);
    }

}
