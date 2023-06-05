package com.jualin.apps.data.remote.response.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("photo")
	val photo: Any,

	@field:SerializedName("id_user")
	val idUser: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("alamat")
	val alamat: Any
)
