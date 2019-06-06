package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmList;

/**
 * Created by sopnil on 28/3/18.
 */

public class ScreenResponse {

    @JsonProperty("message")
    String message;

    @JsonProperty("data")
    RealmList<ScreenInfoModel> listOfScreen;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RealmList<ScreenInfoModel> getListOfScreen() {
        return listOfScreen;
    }

    public void setListOfScreen(RealmList<ScreenInfoModel> listOfScreen) {
        this.listOfScreen = listOfScreen;
    }
}
