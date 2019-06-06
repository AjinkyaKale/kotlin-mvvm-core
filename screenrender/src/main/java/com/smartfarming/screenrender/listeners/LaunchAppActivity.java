package com.smartfarming.screenrender.listeners;

public class LaunchAppActivity {

    private static LaunchAppActivity mInstance;
    private LaunchAppModule launchAppModule;
    private String variety;


    private LaunchAppActivity(LaunchAppModule module) {
        this.launchAppModule = module;
    }

    public static LaunchAppActivity getInstance(LaunchAppModule module) {
        if (mInstance == null) {
            mInstance = new LaunchAppActivity(module);
        }

        return mInstance;
    }

    public LaunchAppModule getLaunchAppModule() {
        return launchAppModule;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }
}
