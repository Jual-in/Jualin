package com.jualin.apps.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("Nama")
    val name: String,

    @field:SerializedName("Harga")
    val price: String,

    @field:SerializedName("Diskon")
    val discount: Double,

    @field:SerializedName("Photo")
    val photoUrl: String?,

    @field:SerializedName("id_umkm")
    val businessId: Int

)
