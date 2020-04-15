package com.ajinkya.mvvmdemo.data.remote.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class LoginResponse(
    @JsonProperty("token")
    val token: String?,

    @JsonProperty("error")
    val error: String?
)

