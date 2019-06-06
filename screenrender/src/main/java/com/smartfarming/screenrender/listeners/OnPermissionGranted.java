package com.smartfarming.screenrender.listeners;

/**
 * Created by sopnil on 15/3/18.
 */

public interface OnPermissionGranted {

    public void onGranted();

    public void onDenied(boolean shouldClose);
}
