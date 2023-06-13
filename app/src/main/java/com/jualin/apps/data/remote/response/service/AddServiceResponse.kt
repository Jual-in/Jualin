package com.jualin.apps.data.remote.response.service

import com.google.gson.annotations.SerializedName
import com.jualin.apps.data.remote.response.search.ServiceResponse

data class AddServiceResponse(
    @field:SerializedName("service")
    val service: ServiceResponse
)
