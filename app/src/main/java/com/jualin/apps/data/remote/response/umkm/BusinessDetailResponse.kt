package com.jualin.apps.data.remote.response.umkm

import com.google.gson.annotations.SerializedName

data class BusinessDetailResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("Nama_usaha")
    val name: String,

    @field:SerializedName("Deskripsi")
    val description: String,

    @field:SerializedName("Kategori")
    val category: String,

    @field:SerializedName("No_hp")
    val phone: String,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null,
)
