package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sopnil on 8/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VarietyModel extends RealmObject {

    @PrimaryKey
    @JsonProperty("variety_type")
    String variety;

    @JsonProperty("water_availability")
    String waterAvailability;

    @JsonProperty("yield_potential")
    String yieldPotential;

    @JsonProperty("screen_id")
    String screenId;

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getWaterAvailability() {
        return waterAvailability;
    }

    public void setWaterAvailability(String waterAvailability) {
        this.waterAvailability = waterAvailability;
    }

    public String getYieldPotential() {
        return yieldPotential;
    }

    public void setYieldPotential(String yieldPotential) {
        this.yieldPotential = yieldPotential;
    }

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }
}
