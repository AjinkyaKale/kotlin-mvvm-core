
package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionAdviceModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Required
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("advice")
    private String advice;
    @JsonProperty("notification_text")
    private String notificationText;
    @JsonProperty("days")
    private String days;
    @JsonProperty("date")
    private String date;
    @JsonProperty("type")
    private String type;

    @Required
    @JsonProperty("screen_id")
    private String screen_id;

    private final static long serialVersionUID = -7615359925582311189L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScreen_id() {
        return screen_id;
    }

    public void setScreen_id(String screen_id) {
        this.screen_id = screen_id;
    }
}
