package com.smartfarming.screenrender.fragment;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.smartfarming.screenrender.activity.BaseActivity;
import com.smartfarming.screenrender.manager.ScreenManager;
import com.smartfarming.screenrender.util.OnHtmlLinkClickListener;
import com.smartfarming.screenrender.util.Utility;

public class BaseDialogFragment extends DialogFragment implements OnHtmlLinkClickListener {

    public String title_label;

    public BaseDialogFragment() {
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
        String screen_id = Utility.isInteger(getActivity(), clickedText);
        if (!TextUtils.isEmpty(screen_id)) {
            launchFragment(screen_id);
        }

    }

}
