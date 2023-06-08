package com.jualin.apps.data.remote.response.umkm

import com.google.gson.annotations.SerializedName

data class AddUMKMResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("umkm")
	val umkm: Umkm
)

data class Umkm(

	@field:SerializedName("Kategori")
	val kategori: String,

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("Nama_usaha")
	val namaUsaha: String,

	@field:SerializedName("Deskripsi")
	val deskripsi: String,

	@field:SerializedName("No_hp")
	val noHp: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("longitude")
	val longitude: Any
)

data class User(

	@field:SerializedName("createdAt")
	val createdAt: String,

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
	val alamat: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
