
package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageModel extends RealmObject implements Serializable {

    @JsonProperty("info")
    private String info;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    //@JsonProperty("link_to")
    private int linkTo;
    private final static long serialVersionUID = -8316067201924122140L;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(int linkTo) {
        this.linkTo = linkTo;
    }

}
