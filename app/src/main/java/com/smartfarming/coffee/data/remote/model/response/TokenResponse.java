package com.smartfarming.coffee.data.remote.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartfarming.coffee.data.remote.model.Token;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenResponse implements Serializable {
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private Token tokenModel;
    private final static long serialVersionUID = -7135924875689864319L;

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    public Token getTokenModel() {
        return tokenModel;
    }

    public void setTokenModel(Token tokenModel) {
        this.tokenModel = tokenModel;
    }
}
