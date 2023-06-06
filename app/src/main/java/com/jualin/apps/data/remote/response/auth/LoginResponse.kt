package com.jualin.apps.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("token")
    val token: String
)
