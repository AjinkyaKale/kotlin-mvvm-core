package com.smartfarming.coffee.data.remote.model.request

data class TokenRequest(
    val clientID: Int,
    var secret: String
)

