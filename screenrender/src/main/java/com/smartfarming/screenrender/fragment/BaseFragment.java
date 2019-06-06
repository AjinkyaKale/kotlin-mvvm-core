package com.smartfarming.screenrender.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.smartfarming.screenrender.activity.BaseActivity;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.util.AppUtility;
import com.smartfarming.screenrender.util.Constants;
import com.smartfarming.screenrender.util.OnHtmlLinkClickListener;
import com.smartfarming.screenrender.util.Utility;


public class BaseFragment extends Fragment implements OnHtmlLinkClickListener {

    public String title_label;

    public BaseFragment() {
    }


    public void launchFragment(String screen_id) {
        if (!TextUtils.isEmpty(screen_id) && !"0".equalsIgnoreCase(screen_id)) {
            Fragment fragment = ScreenManager.getFragmentForId(screen_id);
            if (fragment != null) {
                if (fragment instanceof DialogFragment) {
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.show(getFragmentManager(), dialogFragment.getClass().getName());
                } else {
                    ((BaseActivity) getActivity()).replaceFragment(fragment);
                }
            } else {
                /// show default fragment when fragment  is null.
                showDefaultFragment();
            }
        } else {
            /// show default fragment when screen id is empty.
            showDefaultFragment();
        }
    }


   /* public void launchFragment(String screen_id) {
        if (!TextUtils.isEmpty(screen_id) && !"0".equalsIgnoreCase(screen_id)) {
            Fragment fragment = ScreenManager.getFragmentForId(screen_id);

            if (screen_id.equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.FERTILIZATION))){
                Bundle bundle = fragment.getArguments();
                bundle.putString(LaunchInfoFragment.FROM, LaunchInfoFragment.FERTILIZATION);
                bundle.putString("VIA", "DA");
                fragment.setArguments(bundle);
            }else if (screen_id.equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.SPACING_SCREEN))){
                Bundle bundle = fragment.getArguments();
                bundle.putString(LaunchInfoFragment.FROM, LaunchInfoFragment.SPACING_SCREEN);
                fragment.setArguments(bundle);
            }else if (screen_id.equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.IRRIGATION))){
                Bundle bundle = fragment.getArguments();
                bundle.putString(LaunchInfoFragment.FROM, LaunchInfoFragment.IRRIGATION);
                fragment.setArguments(bundle);
            }

            if (fragment != null) {
                if (fragment instanceof DialogFragment) {
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.show(getFragmentManager(), dialogFragment.getClass().getName());
                } else {
                    ((BaseActivity) getActivity()).replaceFragment(fragment);
                }
            } else {
                /// show default fragment when fragment  is null.
                showDefaultFragment();
            }
        } else {
            /// show default fragment when screen id is empty.
            showDefaultFragment();
        }
    }*/


    private void showDefaultFragment() {
        ((BaseActivity) getActivity()).replaceFragment(new DefaultFragment());
    }


    /**
     * click text on hyperlink
     *
     * @param clickedText
     */
    @Override
    public void onHtmlLinkClickListener(String clickedText) {
//        String screen_id = Utility.isInteger(getActivity(), clickedText);
//        if (!TextUtils.isEmpty(screen_id)) {
//            launchFragment(screen_id);
//        }

        if (!TextUtils.isEmpty(clickedText)) {
            try {
                if (clickedText.equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.FERTILIZATION))) {
                    ((BaseActivity) getActivity()).launchAppFragmentViaDailyAdvice(LaunchInfoFragment.FERTILIZATION);
                } else if (clickedText.equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.IDENTIFICATION))) {
                    ((BaseActivity) getActivity()).launchAppFragmentViaDailyAdvice(LaunchInfoFragment.IDENTIFICATION);
                } else if (clickedText.equalsIgnoreCase(com.smartfarming.screenrender.database.RealmController.with().getScreenIdByLaunchModule(LaunchInfoFragment.IRRIGATION))) {
                    ((BaseActivity) getActivity()).launchAppFragmentViaDailyAdvice(LaunchInfoFragment.IRRIGATION);
                } else if (clickedText.equalsIgnoreCase(ScreenManager.getScreenForPreSowing(Constants.SPACING_SCREEN))) {
                    ((BaseActivity) getActivity()).launchAppFragmentViaDailyAdvice(LaunchInfoFragment.SPACING_SCREEN);
                } else if (clickedText.equalsIgnoreCase(ScreenManager.getScreenForPreSowing(Constants.CHOOSE_YOUR_VARIETY))) {
                    ((BaseActivity) getActivity()).launchAppFragmentViaDailyAdvice(LaunchInfoFragment.CHOOSE_VARIETY);
                } else {
                    launchFragment(clickedText);
                }

            } catch (Exception e) {
                if (Utility.isValidURL(clickedText.trim())) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(clickedText.trim()));
                    getActivity().startActivity(i);
                } else {
                    AppUtility.showAlertDialog(getActivity(), "URL associated with this word is invalid");
                }
            }
        }
    }


    public String getIdForClick() {
        String screen_id = null;


        return screen_id;
    }

}
