package com.smartfarming.screenrender.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by harshadbagul on 23/6/17.
 */

public class PheromoneTrapDateModel extends RealmObject {

    @PrimaryKey
    private String screenId;

    @Required
    private String trapDate;

    @Required
    private String luresDate;


    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }


    public String getTrapDate() {
        return trapDate;
    }

    public void setTrapDate(String trapDate) {
        this.trapDate = trapDate;
    }

    public String getLuresDate() {
        return luresDate;
    }

    public void setLuresDate(String luresDate) {
        this.luresDate = luresDate;
    }
}
