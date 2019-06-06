package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sopnil on 8/3/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigData extends RealmObject {

//    @PrimaryKey
//    private int id = 1;

    @PrimaryKey
    private int id;

    @JsonProperty("variety_selection_setting")
    private RealmList<VarietySelection> listVarietySelectionCombinations;

    @JsonProperty("state")
    private RealmList<StateModel> listOfStates;

    @JsonProperty("soil_type")
    private RealmList<String> listSoilTypes;

    @JsonProperty("rain_type")
    private RealmList<String> listRainTypes;

    @JsonProperty("weather_type")
    private RealmList<String> listWeatherTypes;

    @JsonProperty("temperature_type")
    private RealmList<String> listTemperatureTypes;

    @JsonProperty("region_type")
    private RealmList<String> listRegionTypes;

    @JsonProperty("variety_type")
    private RealmList<String> listVarietyTypes;

    @JsonProperty("storage_type")
    private RealmList<String> listStorageTypes;

    @JsonProperty("fertilizer_type")
    private RealmList<String> listFertilizerTypes;

    @JsonProperty("spraying_type")
    private RealmList<String> listSprayingTypes;

    @JsonProperty("irrigation_type")
    private RealmList<String> listIrrigationTypes;

    @JsonProperty("farming_type")
    private RealmList<String> listPurposeTypes;

    @JsonProperty("water_availibility")
    private RealmList<String> listWaterAvailability;

    @JsonProperty("crop_type")
    private RealmList<String> listCropTypes;

    @JsonProperty("method_type")
    private RealmList<String> listMethodTypes;

    @JsonProperty("advice_type")
    private RealmList<String> listAdviceTypes;

    @JsonProperty("certification_type")
    private RealmList<String> listCertificationTypes;

    @JsonProperty("identification")
    private RealmList<IdentificationModel> listOfIdentification;

    @JsonProperty("pre_sowing")
    //private RealmList<PreSowingModel> listOfPreSowing;
    private PreSowingModel preSowingModel;

    @JsonProperty("variety_launch_data")
    private RealmList<VarietyModel> listOfVarieties;

    @JsonProperty("module_launch")
    private LaunchModule launchModule;

    @JsonProperty("spraying_offline_message")
    private RealmList<String> sprayingOfflineMessage;

    @JsonProperty("information_data")
    private InformationModel informationModel;

    public InformationModel getInformationModel() {
        return informationModel;
    }

    public void setInformationModel(InformationModel informationModel) {
        this.informationModel = informationModel;
    }

    public RealmList<String> getSprayingOfflineMessage() {
        return sprayingOfflineMessage;
    }

    public void setSprayingOfflineMessage(RealmList<String> sprayingOfflineMessage) {
        this.sprayingOfflineMessage = sprayingOfflineMessage;
    }

    public LaunchModule getLaunchModule() {
        return launchModule;
    }

    public RealmList<String> getListMethodTypes() {
        return listMethodTypes;
    }

    public void setListMethodTypes(RealmList<String> listMethodTypes) {
        this.listMethodTypes = listMethodTypes;
    }

    public void setLaunchModule(LaunchModule launchModule) {
        this.launchModule = launchModule;
    }

    public RealmList<IdentificationModel> getListOfIdentification() {
        return listOfIdentification;
    }

    public void setListOfIdentification(RealmList<IdentificationModel> listOfIdentification) {
        this.listOfIdentification = listOfIdentification;
    }

    public RealmList<VarietySelection> getListVarietySelectionCombinations() {
        return listVarietySelectionCombinations;
    }

    public void setListVarietySelectionCombinations(RealmList<VarietySelection> listVarietySelectionCombinations) {
        this.listVarietySelectionCombinations = listVarietySelectionCombinations;
    }

    public RealmList<String> getListSoilTypes() {
        return listSoilTypes;
    }

    public void setListSoilTypes(RealmList<String> listSoilTypes) {
        this.listSoilTypes = listSoilTypes;
    }

    public RealmList<StateModel> getListOfStates() {
        return listOfStates;
    }

    public void setListOfStates(RealmList<StateModel> listOfStates) {
        this.listOfStates = listOfStates;
    }

    public RealmList<String> getListRainTypes() {
        return listRainTypes;
    }

    public void setListRainTypes(RealmList<String> listRainTypes) {
        this.listRainTypes = listRainTypes;
    }

    public RealmList<String> getListWeatherTypes() {
        return listWeatherTypes;
    }

    public void setListWeatherTypes(RealmList<String> listWeatherTypes) {
        this.listWeatherTypes = listWeatherTypes;
    }

    public RealmList<String> getListTemperatureTypes() {
        return listTemperatureTypes;
    }

    public void setListTemperatureTypes(RealmList<String> listTemperatureTypes) {
        this.listTemperatureTypes = listTemperatureTypes;
    }

    public RealmList<String> getListRegionTypes() {
        return listRegionTypes;
    }

    public void setListRegionTypes(RealmList<String> listRegionTypes) {
        this.listRegionTypes = listRegionTypes;
    }

    public RealmList<String> getListVarietyTypes() {
        return listVarietyTypes;
    }

    public void setListVarietyTypes(RealmList<String> listVarietyTypes) {
        this.listVarietyTypes = listVarietyTypes;
    }

    public RealmList<String> getListStorageTypes() {
        return listStorageTypes;
    }

    public void setListStorageTypes(RealmList<String> listStorageTypes) {
        this.listStorageTypes = listStorageTypes;
    }

    public RealmList<String> getListFertilizerTypes() {
        return listFertilizerTypes;
    }

    public void setListFertilizerTypes(RealmList<String> listFertilizerTypes) {
        this.listFertilizerTypes = listFertilizerTypes;
    }

    public RealmList<String> getListSprayingTypes() {
        return listSprayingTypes;
    }

    public void setListSprayingTypes(RealmList<String> listSprayingTypes) {
        this.listSprayingTypes = listSprayingTypes;
    }

    public RealmList<String> getListIrrigationTypes() {
        return listIrrigationTypes;
    }

    public void setListIrrigationTypes(RealmList<String> listIrrigationTypes) {
        this.listIrrigationTypes = listIrrigationTypes;
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

    public RealmList<String> getListCropTypes() {
        return listCropTypes;
    }

    public void setListCropTypes(RealmList<String> listCropTypes) {
        this.listCropTypes = listCropTypes;
    }

    public RealmList<String> getListAdviceTypes() {
        return listAdviceTypes;
    }

    public void setListAdviceTypes(RealmList<String> listAdviceTypes) {
        this.listAdviceTypes = listAdviceTypes;
    }

    public RealmList<String> getListCertificationTypes() {
        return listCertificationTypes;
    }

    public void setListCertificationTypes(RealmList<String> listCertificationTypes) {
        this.listCertificationTypes = listCertificationTypes;
    }

//    public RealmList<PreSowingModel> getListOfPreSowing() {
//        return listOfPreSowing;
//    }
//
//    public void setListOfPreSowing(RealmList<PreSowingModel> listOfPreSowing) {
//        this.listOfPreSowing = listOfPreSowing;
//    }


    public PreSowingModel getPreSowingModel() {
        return preSowingModel;
    }

    public void setPreSowingModel(PreSowingModel preSowingModel) {
        this.preSowingModel = preSowingModel;
    }

    public RealmList<VarietyModel> getListOfVarieties() {
        return listOfVarieties;
    }

    public void setListOfVarieties(RealmList<VarietyModel> listOfVarieties) {
        this.listOfVarieties = listOfVarieties;
    }
}
