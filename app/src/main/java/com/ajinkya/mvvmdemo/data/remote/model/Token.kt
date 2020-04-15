package com.ajinkya.mvvmdemo.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class Token : Serializable {

    @JsonProperty("token_type")
    var tokenType: String? = null

    @JsonProperty("expires_in")
    var expiresIn: Int = 0

    @JsonProperty("access_token")
    var accessToken: String? = null
}
