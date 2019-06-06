package com.smartfarming.screenrender.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import com.smartfarming.screenrender.R;
import com.smartfarming.screenrender.listeners.OnImageDownloadComplete;
import com.smartfarming.screenrender.manager.FileManager;
import com.smartfarming.screenrender.manager.LanguageManager;
import com.smartfarming.screenrender.model.DownloadImageModel;
import com.smartfarming.screenrender.service.DownloadImageService;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.LabelConstant;
import dmax.dialog.SpotsDialog;

/**
 * Created by sopnil on 28/2/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static final String IMAGE_DOWNLOAD_PROGRESS = "image_download_progress";
    protected SpotsDialog mSpotDialog = null;
    private int mFragmentFrameId;
    private OnImageDownloadComplete completeListener;
    private LocalBroadcastManager bManager;

    public static final String LAUNCH_DASHBOARD = "launch_dashboard";

    public abstract void launchAppFragment(String type);

    public abstract void launchAppFragmentViaDailyAdvice(String type);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * is used to replace the fragment in the conatiner specified
     * in replace method.
     *
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {

        if (fragment == null) return;

        String className = fragment.getClass().getName();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, 0, 0, R.anim.exit_to_right);
        fragmentTransaction.addToBackStack(className);
        fragmentTransaction.replace(getFragmentFrameId(), fragment, className).commit();

    }

    public int getFragmentFrameId() {
        return mFragmentFrameId;
    }

    public void setFragmentFrameId(int layoutId) {
        mFragmentFrameId = layoutId;
    }

    /**
     * show the progress dialog
     *
     * @param message
     */
    protected void startProgress(String message) {
        mSpotDialog = new SpotsDialog(this, message);
        mSpotDialog.setCancelable(false);
        mSpotDialog.setCanceledOnTouchOutside(false);
        mSpotDialog.show();
    }


    /**
     * dissmiss the progress dialog
     */
    protected void endProgress() {
        if (mSpotDialog != null && mSpotDialog.isShowing()) {
            mSpotDialog.dismiss();
        }
    }


    /**
     * Start Download images service
     */

    public void startDownload(String[] path, OnImageDownloadComplete onImageDownloadComplete) {
        this.completeListener = onImageDownloadComplete;
        if (AppUtility.isNetworkAvailable(this)) {
            if (FileManager.isMemoryAvailableForDownload(this)) {
                LanguageManager mLanguageManager = LanguageManager.getInstance(getApplicationContext());
                registerReceiver();
                startProgress(mLanguageManager.getLabel(LabelConstant.DOWNLOADING_IMAGES));
                Intent intent = new Intent(this, DownloadImageService.class);
                intent.putExtra(DownloadImageService.EXTRA_PATH, path);
                startService(intent);
            }
        } else {
            AppUtility.showAlertDialog(this, getString(R.string.no_internet));
        }

    }

    /**
     * Register local broadcast receiver
     * to display progress
     */
    protected void registerReceiver() {

        bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IMAGE_DOWNLOAD_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }


    /**
     * Broadcast receiver to get progress of image downloading
     */
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                if (intent.getAction().equals(IMAGE_DOWNLOAD_PROGRESS)) {

                    DownloadImageModel downloadImageModel = intent.getParcelableExtra("downloadImageModel");
                    if (downloadImageModel.getDownloadStatus().equals(DownloadImageService.DOWNLOAD_FAILED)) {
                        endProgress();
                        AppUtility.showDialogAndFinish(BaseActivity.this, getString(R.string.image_download_fail));
                    } else if (downloadImageModel.getDownloadStatus().equals(DownloadImageService.DOWNLOAD_PROGRESS)) {
                        if (mSpotDialog != null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Getting app images.. ").append(downloadImageModel.getProgress()).append("%");
                            mSpotDialog.setMessage(stringBuilder.toString());
                        }
                    } else if (downloadImageModel.getDownloadStatus().equals(DownloadImageService.DOWNLOAD_COMPLETED)) {
                        if (downloadImageModel.getProgress() == 100) {
                            //Save last fetch date for Images download
                            //AppPreferencesHelper.getInstance(context).setLastFetchImagesDate(Utility.getCurrentDateAndTime());
                            endProgress();
                            //resetAllFlag();
                            //nextScreen();
                            bManager.unregisterReceiver(broadcastReceiver);
                            completeListener.onImageDownloadingComplete();
                        }
                    } else if (downloadImageModel.getDownloadStatus().equals(DownloadImageService.EXTRACTING_IMAGES)) {
                        startProgress("Extracting images.. " + downloadImageModel.getProgress());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public void popUpFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
            finish();
        }
    }

    /**
     * on back press of base activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }
}
