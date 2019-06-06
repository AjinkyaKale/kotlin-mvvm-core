package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sopnil on 9/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VarietySelection extends RealmObject {

    @PrimaryKey
    @JsonProperty("setting_id")
    String id;

    //
//    @JsonProperty("value")
//    VarietyQuestionnaire questionnaire;
//
//    @JsonProperty("variety")
//    RealmList<VarietyModel> listOfSuggestedVarieties;
//
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//
//    public VarietyQuestionnaire getQuestionnaire() {
//        return questionnaire;
//    }
//
//    public void setQuestionnaire(VarietyQuestionnaire questionnaire) {
//        this.questionnaire = questionnaire;
//    }
//
//    public RealmList<VarietyModel> getListOfSuggestedVarieties() {
//        return listOfSuggestedVarieties;
//    }
//
//    public void setListOfSuggestedVarieties(RealmList<VarietyModel> listOfSuggestedVarieties) {
//        this.listOfSuggestedVarieties = listOfSuggestedVarieties;
//    }

    @JsonProperty("region")
    private RealmList<String> listRegionTypes;

    @JsonProperty("method")
    private RealmList<String> listMethodTypes;

    @JsonProperty("crop_type")
    private RealmList<String> listCropTypes;

    @JsonProperty("purpose")
    private RealmList<String> listPurposeTypes;

    @JsonProperty("water_availability")
    private RealmList<String> listWaterAvailability;

    @JsonProperty("variety_type")
    String VarietyType;

    public RealmList<String> getListRegionTypes() {
        return listRegionTypes;
    }

    public void setListRegionTypes(RealmList<String> listRegionTypes) {
        this.listRegionTypes = listRegionTypes;
    }

    public RealmList<String> getListMethodTypes() {
        return listMethodTypes;
    }

    public void setListMethodTypes(RealmList<String> listMethodTypes) {
        this.listMethodTypes = listMethodTypes;
    }

    public RealmList<String> getListCropTypes() {
        return listCropTypes;
    }

    public void setListCropTypes(RealmList<String> listCropTypes) {
        this.listCropTypes = listCropTypes;
    }

    public RealmList<String> getListPurposeTypes() {
        return listPurposeTypes;
    }

    public void setListPurposeTypes(RealmList<String> listPurposeTypes) {
        this.listPurposeTypes = listPurposeTypes;
    }

    public RealmList<String> getListWaterAvailability() {
        return listWaterAvailability;
    }

    public void setListWaterAvailability(RealmList<String> listWaterAvailability) {
        this.listWaterAvailability = listWaterAvailability;
    }

    public String getVarietyType() {
        return VarietyType;
    }

    public void setVarietyType(String varietyType) {
        VarietyType = varietyType;
    }
}
