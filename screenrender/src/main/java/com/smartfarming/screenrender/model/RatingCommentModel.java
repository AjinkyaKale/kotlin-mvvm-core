package com.smartfarming.screenrender.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by harshadbagul on 6/6/17.
 */

public class RatingCommentModel extends RealmObject {

    @PrimaryKey
    private String screenId;

    private String rating;
    private String comment;
    private boolean isSync;
    @Required
    private String selectedButton;

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getSelectedButton() {
        return selectedButton;
    }

    public void setSelectedButton(String selectedButton) {
        this.selectedButton = selectedButton;
    }
}
