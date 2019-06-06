package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.realm.RealmObject;

/**
 * Created by sopnil on 10/4/18.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LaunchModule extends RealmObject {

    @JsonProperty("identification")
    String identification;

    @JsonProperty("organic")
    String organic;

    @JsonProperty("irrigation")
    String irrigation;

    @JsonProperty("fertilizer")
    String fertilizer;

    @JsonProperty("curative_spray")
    String curativeSpray;

    @JsonProperty("learn")
    String learnHowToSpray;

    @JsonProperty("mancozeb")
    String mancozeb;

    @JsonProperty("neem_spray")
    String neemSpray;

    @JsonProperty("revus")
    String revus;

    @JsonProperty("sectin_spray")
    String sectinSpray;

    @JsonProperty("blight_damage_spray")
    String blightDamage;


    public String getBlightDamage() {
        return blightDamage;
    }

    public void setBlightDamage(String blightDamage) {
        this.blightDamage = blightDamage;
    }

    public String getSectinSpray() {
        return sectinSpray;
    }

    public void setSectinSpray(String sectinSpray) {
        this.sectinSpray = sectinSpray;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(String fertilizer) {
        this.fertilizer = fertilizer;
    }

    public String getOrganic() {
        return organic;
    }

    public void setOrganic(String organic) {
        this.organic = organic;
    }

    public String getCurativeSpray() {
        return curativeSpray;
    }

    public void setCurativeSpray(String curativeSpray) {
        this.curativeSpray = curativeSpray;
    }

    public String getLearnHowToSpray() {
        return learnHowToSpray;
    }

    public void setLearnHowToSpray(String learnHowToSpray) {
        this.learnHowToSpray = learnHowToSpray;
    }

    public String getMancozeb() {
        return mancozeb;
    }

    public void setMancozeb(String mancozeb) {
        this.mancozeb = mancozeb;
    }

    public String getNeemSpray() {
        return neemSpray;
    }

    public void setNeemSpray(String neemSpray) {
        this.neemSpray = neemSpray;
    }

    public String getRevus() {
        return revus;
    }

    public void setRevus(String revus) {
        this.revus = revus;
    }
}
