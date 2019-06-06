package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

/**
 * Created by sopnil on 9/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VarietyQuestionnaire extends RealmObject {

    @JsonProperty("region")
    String regionType;

    @JsonProperty("crop_type")
    String cropType;

    @JsonProperty("purpose")
    String purpose;

    @JsonProperty("water_availability")
    String waterAvailability;

    @JsonProperty("yield_potential")
    String yieldPotential;

    @JsonProperty("description")
    String description;

    @JsonProperty("method")
    String methodType;

    @JsonProperty("q1")
    String questionOne;

    @JsonProperty("q2")
    String questionTwo;

    @JsonProperty("q3")
    String questionThree;

    @JsonProperty("q4")
    String questionFour;


    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getQuestionOne() {
        return questionOne;
    }

    public void setQuestionOne(String questionOne) {
        this.questionOne = questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public void setQuestionTwo(String questionTwo) {
        this.questionTwo = questionTwo;
    }

    public String getQuestionThree() {
        return questionThree;
    }

    public void setQuestionThree(String questionThree) {
        this.questionThree = questionThree;
    }

    public String getQuestionFour() {
        return questionFour;
    }

    public void setQuestionFour(String questionFour) {
        this.questionFour = questionFour;
    }
}
