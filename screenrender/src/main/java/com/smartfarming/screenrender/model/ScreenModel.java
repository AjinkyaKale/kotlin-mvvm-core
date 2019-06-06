
package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Required
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("screen_type")
    private String screenType;
    @JsonProperty("texts")
    private RealmList<TextModel> textModels = null;
    @JsonProperty("images")
    private RealmList<ImageModel> imageModels = null;
    @JsonProperty("buttons")
    private RealmList<ButtonModel> buttonsModels = null;
    @JsonProperty("tabs")
    private RealmList<TabModel> tabs = null;
    private final static long serialVersionUID = 7653030893062825097L;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    public RealmList<TextModel> getTextModels() {
        return textModels;
    }

    public void setTextModels(RealmList<TextModel> textModels) {
        this.textModels = textModels;
    }

    public RealmList<ImageModel> getImageModels() {
        return imageModels;
    }

    public void setImageModels(RealmList<ImageModel> imageModels) {
        this.imageModels = imageModels;
    }

    public RealmList<ButtonModel> getButtonsModels() {
        return buttonsModels;
    }

    public void setButtonsModels(RealmList<ButtonModel> buttonsModels) {
        this.buttonsModels = buttonsModels;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<TabModel> getTabs() {
        return tabs;
    }

    public void setTabs(RealmList<TabModel> tabs) {
        this.tabs = tabs;
    }
}
