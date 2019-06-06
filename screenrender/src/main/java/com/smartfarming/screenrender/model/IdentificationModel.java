package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sopnil on 31/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentificationModel extends RealmObject {

    @PrimaryKey
    @JsonProperty("variety_type")
    String varietyType;

    @JsonProperty("STAGE1")
    String sproutNVegetativeGrowthStage;

    @JsonProperty("STAGE2")
    String tuberInitiationStage;

    @JsonProperty("STAGE3")
    String bulkingStage;

    @JsonProperty("STAGE4")
    String ripeningStage;

    public String getVarietyType() {
        return varietyType;
    }

    public void setVarietyType(String varietyType) {
        this.varietyType = varietyType;
    }

    public String getSproutNVegetativeGrowthStage() {
        return sproutNVegetativeGrowthStage;
    }

    public void setSproutNVegetativeGrowthStage(String sproutNVegetativeGrowthStage) {
        this.sproutNVegetativeGrowthStage = sproutNVegetativeGrowthStage;
    }

    public String getTuberInitiationStage() {
        return tuberInitiationStage;
    }

    public void setTuberInitiationStage(String tuberInitiationStage) {
        this.tuberInitiationStage = tuberInitiationStage;
    }

    public String getBulkingStage() {
        return bulkingStage;
    }

    public void setBulkingStage(String bulkingStage) {
        this.bulkingStage = bulkingStage;
    }

    public String getRipeningStage() {
        return ripeningStage;
    }

    public void setRipeningStage(String ripeningStage) {
        this.ripeningStage = ripeningStage;
    }
}
