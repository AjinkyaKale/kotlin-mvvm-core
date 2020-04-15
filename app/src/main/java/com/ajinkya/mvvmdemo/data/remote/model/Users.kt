package com.ajinkya.mvvmdemo.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class Users(

    @JsonProperty("id")
    val id: Int?,

    @JsonProperty("avatar")
    val mAvatar: String? = null,

    @JsonProperty("email")
    val mEmail: String? = null,

    @JsonProperty("first_name")
    val mFirstName: String? = null,

    @JsonProperty("last_name")
    val mLastName: String? = null
)
