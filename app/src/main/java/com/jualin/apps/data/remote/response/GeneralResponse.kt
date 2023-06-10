package com.jualin.apps.data.remote.response

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @field:SerializedName("message")
    val message: String,
)
