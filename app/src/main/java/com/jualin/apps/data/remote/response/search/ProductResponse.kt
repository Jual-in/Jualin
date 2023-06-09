package com.jualin.apps.data.remote.response.search

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("Nama")
    val name: String,

    @field:SerializedName("Harga")
    val price: Int,

    @field:SerializedName("Diskon")
    val discount: Int,

    @field:SerializedName("Photo")
    val photoUrl: String?,

    @field:SerializedName("id_umkm")
    val businessId: Int?

)
