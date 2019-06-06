package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import java.io.Serializable;

/**
 * Created by dhanrajchoudhari on 25/5/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserModel extends RealmObject implements Serializable {

    @PrimaryKey
    @Required
    @JsonProperty("id")
    private String id;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("phone_no")
    private String phoneNo;
    @JsonProperty("dob")
    private String birthday;
    /*@JsonProperty("gender")
    private int gender;*/
    @JsonProperty("latitude")
    private String latitude;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("place")
    private String place;
    /*@JsonProperty("farm_area")
    private String totalFarmArea;*/
    @JsonProperty("total_cotton_area")
    private String totalCottonArea;
    @JsonProperty("device_id")
    String deviceID;
    @JsonProperty("sowing_date")
    private String sowingDate;
    @JsonProperty("certification")
    private String certification;
    @JsonProperty("soil_type")
    private String soilType;
    @JsonProperty("seed_type")
    private String seedType;
    @JsonProperty("irrigation_type")
    private String irrigationSystem;
    @JsonProperty("pump_capacity")
    private String pumpCapacity;
    @JsonProperty("farming_type")
    private String farmingType;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("name")
    private String name;
    @JsonProperty("state")
    private String stateKey;
    @JsonProperty("region")
    private String regionKey;

    /* @JsonProperty("access_to_hire_labour")
     private String hiredLabour;

     @JsonProperty("income")
     private float incomePerYear;

     @JsonProperty("house_hold_size")
     private int houseHoldSize;
     @JsonProperty("member_count_on_farm")
     private int familyMemberWorker;
 */
    private String pumpVolume;
    private String pumpTime;

    /*@JsonProperty("labour_wages")
    private int moneySpentOnLabours;

    @JsonProperty("labour_age")
    private int labourAverageAge;*/

    @JsonProperty("variety")
    private String varietyKey;

    @JsonProperty("purpose")
    private String purposeKey;

    @JsonProperty("storage_type")
    private String storageType;

    @JsonProperty("potato_area")
    private String totalPotatoArea;

    /*@JsonProperty("irrigated_area")
    private String totalIrrigatedPotatoArea;*/

    @JsonProperty("sprinkler_rotations")
    private int numberOfTimesSprinklerMoved;

    @JsonProperty("season")
    private String season;

    private String manualPumpCapacity;

    @JsonIgnore
    private int positionState;

    @JsonIgnore
    private int positionRegion;

    @JsonIgnore
    private int positionPurpose;

    @JsonIgnore
    private int positionVariety;

    @JsonIgnore
    private int positionCertification;

    @JsonIgnore
    private int positionIrrigation;

    @JsonIgnore
    private int positionStorageFacility;

    @JsonIgnore
    private int positionSoilType;

    @JsonIgnore
    private boolean valid;

    //private int posCertification;

    //private int posSoilType;

    //private int posIrrigationSystem;

    //private int posFarmingType;

    private final static long serialVersionUID = 8236095580552967237L;

    public int getPositionPurpose() {
        return positionPurpose;
    }

    public int getPositionSoilType() {
        return positionSoilType;
    }

    public void setPositionSoilType(int positionSoilType) {
        this.positionSoilType = positionSoilType;
    }

    public void setPositionPurpose(int positionPurpose) {
        this.positionPurpose = positionPurpose;
    }

    public int getPositionVariety() {
        return positionVariety;
    }

    public void setPositionVariety(int positionVariety) {
        this.positionVariety = positionVariety;
    }

    public int getPositionCertification() {
        return positionCertification;
    }

    public void setPositionCertification(int positionCertification) {
        this.positionCertification = positionCertification;
    }

    public int getPositionIrrigation() {
        return positionIrrigation;
    }

    public void setPositionIrrigation(int positionIrrigation) {
        this.positionIrrigation = positionIrrigation;
    }

    public int getPositionStorageFacility() {
        return positionStorageFacility;
    }

    public void setPositionStorageFacility(int positionStorageFacility) {
        this.positionStorageFacility = positionStorageFacility;
    }

    public int getPositionRegion() {
        return positionRegion;
    }

    public void setPositionRegion(int positionRegion) {
        this.positionRegion = positionRegion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

   /* public int getHouseHoldSize() {
        return houseHoldSize;
    }

    public void setHouseHoldSize(int houseHoldSize) {
        this.houseHoldSize = houseHoldSize;
    }

    public float getIncomePerYear() {
        return incomePerYear;
    }

    public void setIncomePerYear(float incomePerYear) {
        this.incomePerYear = incomePerYear;
    }*/

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

   /* public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }*/

    public String getPurposeKey() {
        return purposeKey;
    }

    public void setPurposeKey(String purposeKey) {
        this.purposeKey = purposeKey;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getVarietyKey() {
        return varietyKey;
    }

    public String getTotalPotatoArea() {
        return totalPotatoArea;
    }

    public void setTotalPotatoArea(String totalPotatoArea) {
        this.totalPotatoArea = totalPotatoArea;
    }

    public int getPositionState() {
        return positionState;
    }

    public void setPositionState(int positionState) {
        this.positionState = positionState;
    }

    public void setVarietyKey(String varietyKey) {
        this.varietyKey = varietyKey;
    }

   /* public String getHiredLabour() {
        return hiredLabour;
    }

    public int getMoneySpentOnLabours() {
        return moneySpentOnLabours;
    }

    public void setMoneySpentOnLabours(int moneySpentOnLabours) {
        this.moneySpentOnLabours = moneySpentOnLabours;
    }*/

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getRegionKey() {
        return regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

   /* public int getLabourAverageAge() {
        return labourAverageAge;
    }

    public void setLabourAverageAge(int labourAverageAge) {
        this.labourAverageAge = labourAverageAge;
    }*/

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

   /* public void setHiredLabour(String hiredLabour) {
        this.hiredLabour = hiredLabour;
    }*/

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

   /* public String getTotalFarmArea() {
        return totalFarmArea;
    }

    public void setTotalFarmArea(String totalFarmArea) {
        this.totalFarmArea = totalFarmArea;
    }*/

    public String getTotalCottonArea() {
        return totalCottonArea;
    }

    public void setTotalCottonArea(String totalCottonArea) {
        this.totalCottonArea = totalCottonArea;
    }

//    public String getTotalIrrigatedCottonArea() {
//        return totalIrrigatedCottonArea;
//    }
//
//    public void setTotalIrrigatedCottonArea(String totalIrrigatedCottonArea) {
//        this.totalIrrigatedCottonArea = totalIrrigatedCottonArea;
//    }


   /* public String getTotalIrrigatedPotatoArea() {
        return totalIrrigatedPotatoArea;
    }

    public void setTotalIrrigatedPotatoArea(String totalIrrigatedPotatoArea) {
        this.totalIrrigatedPotatoArea = totalIrrigatedPotatoArea;
    }*/

    public String getSowingDate() {
        return sowingDate;
    }

    public void setSowingDate(String sowingDate) {
        this.sowingDate = sowingDate;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getSeedType() {
        return seedType;
    }

    public void setSeedType(String seedType) {
        this.seedType = seedType;
    }

    public String getIrrigationSystem() {
        return irrigationSystem;
    }

    public void setIrrigationSystem(String irrigationSystem) {
        this.irrigationSystem = irrigationSystem;
    }

    public String getPumpCapacity() {
        return pumpCapacity;
    }

    public void setPumpCapacity(String pumpCapacity) {
        this.pumpCapacity = pumpCapacity;
    }

    public String getFarmingType() {
        return farmingType;
    }

    public void setFarmingType(String farmingType) {
        this.farmingType = farmingType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPumpVolume() {
        return pumpVolume;
    }

    public void setPumpVolume(String pumpVolume) {
        this.pumpVolume = pumpVolume;
    }

    public String getPumpTime() {
        return pumpTime;
    }

    public void setPumpTime(String pumpTime) {
        this.pumpTime = pumpTime;
    }


//    public int getPosCertification() {
//        return posCertification;
//    }
//
//    public void setPosCertification(int posCertification) {
//        this.posCertification = posCertification;
//    }
//
//    public int getPosSoilType() {
//        return posSoilType;
//    }
//
//    public void setPosSoilType(int posSoilType) {
//        this.posSoilType = posSoilType;
//    }
//
//    public int getPosIrrigationSystem() {
//        return posIrrigationSystem;
//    }
//
//    public void setPosIrrigationSystem(int posIrrigationSystem) {
//        this.posIrrigationSystem = posIrrigationSystem;
//    }
//
//    public int getPosFarmingType() {
//        return posFarmingType;
//    }
//
//    public void setPosFarmingType(int posFarmingType) {
//        this.posFarmingType = posFarmingType;
//    }

    public String getManualPumpCapacity() {
        return manualPumpCapacity;
    }

    public void setManualPumpCapacity(String manualPumpCapacity) {
        this.manualPumpCapacity = manualPumpCapacity;
    }

    /*public int getFamilyMemberWorker() {
        return familyMemberWorker;
    }

    public void setFamilyMemberWorker(int familyMemberWorker) {
        this.familyMemberWorker = familyMemberWorker;
    }*/

    public int getNumberOfTimesSprinklerMoved() {
        return numberOfTimesSprinklerMoved;
    }

    public void setNumberOfTimesSprinklerMoved(int numberOfTimesSprinklerMoved) {
        this.numberOfTimesSprinklerMoved = numberOfTimesSprinklerMoved;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
