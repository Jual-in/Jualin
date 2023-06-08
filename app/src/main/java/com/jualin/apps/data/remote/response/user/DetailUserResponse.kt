package com.jualin.apps.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @field:SerializedName("id_user")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("alamat")
    val address: String,

    @field:SerializedName("photo")
    val photoUrl: String
)