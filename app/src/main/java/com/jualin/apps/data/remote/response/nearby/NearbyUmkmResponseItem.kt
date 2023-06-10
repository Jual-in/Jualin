package com.jualin.apps.data.remote.response.nearby

import com.google.gson.annotations.SerializedName

data class NearbyUmkmResponseItem(

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

    @field:SerializedName("longitude")
    val longitude: Any
)