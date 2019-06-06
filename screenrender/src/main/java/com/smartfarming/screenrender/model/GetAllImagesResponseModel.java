package com.smartfarming.screenrender.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dhanrajchoudhari on 16/6/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAllImagesResponseModel implements Serializable {

    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private GetAllImagesDataModel dataModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GetAllImagesDataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(GetAllImagesDataModel dataModel) {
        this.dataModel = dataModel;
    }
}
