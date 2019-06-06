
package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmList;
import io.realm.RealmObject;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextModel extends RealmObject implements Serializable {

    @JsonProperty("type")
    private String type;
    @JsonProperty("value")
    private String value;
    @JsonProperty("link_to")
    private String linkTo;
    @JsonProperty("buttons")
    private RealmList<ButtonModel> buttonModels = null;
    private final static long serialVersionUID = -5051803070637654247L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public RealmList<ButtonModel> getButtonModels() {
        return buttonModels;
    }

    public void setButtonModels(RealmList<ButtonModel> buttonModels) {
        this.buttonModels = buttonModels;
    }
}
