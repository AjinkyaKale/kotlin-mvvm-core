package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TabModel extends RealmObject implements Serializable {

    @JsonProperty("tab_name")
    private String tabName;
    @JsonProperty("link_to")
    private String linkTo;
    @JsonProperty("order")
    private String order;

    @JsonProperty("tab_name")
    public String getTabName() {
        return tabName;
    }

    @JsonProperty("tab_name")
    public void setTabName(String tabName) {
        this.tabName = tabName;
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
