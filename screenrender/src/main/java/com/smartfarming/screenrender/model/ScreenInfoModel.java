package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by sopnil on 28/3/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenInfoModel extends RealmObject {

    @PrimaryKey
    @Required
    @JsonProperty("id")
    String id;

    @JsonProperty("screen")
    ScreenModel screenModel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ScreenModel getScreenModel() {
        return screenModel;
    }

    public void setScreenModel(ScreenModel screenModel) {
        this.screenModel = screenModel;
    }
}
