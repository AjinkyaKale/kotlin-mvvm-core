package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

/**
 * Created by sopnil on 3/4/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreSowingModel extends RealmObject {

    @JsonProperty("variety")
    String variety;

    @JsonProperty("seed_potato_handling")
    String seedPotatoHandling;

    @JsonProperty("farm_layout")
    String farmLayout;

    @JsonProperty("land_preparation")
    String landPreparation;

    @JsonProperty("spacing_density")
    String spacingDensity;

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getSeedPotatoHandling() {
        return seedPotatoHandling;
    }

    public void setSeedPotatoHandling(String seedPotatoHandling) {
        this.seedPotatoHandling = seedPotatoHandling;
    }

    public String getFarmLayout() {
        return farmLayout;
    }

    public void setFarmLayout(String farmLayout) {
        this.farmLayout = farmLayout;
    }

    public String getLandPreparation() {
        return landPreparation;
    }

    public void setLandPreparation(String landPreparation) {
        this.landPreparation = landPreparation;
    }

    public String getSpacingDensity() {
        return spacingDensity;
    }

    public void setSpacingDensity(String spacingDensity) {
        this.spacingDensity = spacingDensity;
    }
}
