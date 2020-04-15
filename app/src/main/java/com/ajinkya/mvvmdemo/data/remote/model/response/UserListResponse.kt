package com.ajinkya.mvvmdemo.data.remote.model.response

import com.ajinkya.mvvmdemo.data.remote.model.Users
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class UserListResponse(

    @JsonProperty("data")
    val usersList: ArrayList<Users>,

    @JsonProperty("page")
    val page: Long? = null,

    @JsonProperty("per_page")
    val perPage: Long? = null,

    @JsonProperty("total")
    val total: Long? = null,

    @JsonProperty("total_pages")
    val totalPages: Long? = null

)
