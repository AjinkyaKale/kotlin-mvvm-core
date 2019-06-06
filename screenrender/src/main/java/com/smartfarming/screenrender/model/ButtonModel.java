package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

import java.io.Serializable;

/**
 * Created by dhanrajchoudhari on 25/5/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ButtonModel extends RealmObject implements Serializable {

    @JsonProperty("type")
    private String type;
    @JsonProperty("info")
    private String info;
    @JsonProperty("lable")
    private String lable;
    @JsonProperty("link_to")
    private String linkTo;
    @JsonProperty("order")
    private String order;

    private final static long serialVersionUID = 415412963585826198L;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("info")
    public String getInfo() {
        return info;
    }

    @JsonProperty("info")
    public void setInfo(String info) {
        this.info = info;
    }

    @JsonProperty("lable")
    public String getLable() {
        return lable;
    }

    @JsonProperty("lable")
    public void setLable(String lable) {
        this.lable = lable;
    }

    @JsonProperty("link_to")
    public String getLinkTo() {
        return linkTo;
    }

    @JsonProperty("link_to")
    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    @JsonProperty("order")
    public String getOrder() {
        return order;
    }

    @JsonProperty("order")
    public void setOrder(String order) {
        this.order = order;
    }
}
