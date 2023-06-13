package com.jualin.apps.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Business(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val phone: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
) : Parcelable

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val discount: Int,
    val photoUrl: String,

    val businessId: Int? = 0,
)

data class Service(
    val id: Int,
    val name: String,
    val price: Int,
    val discount: Int,

    val businessId: Int? = 0,
)
