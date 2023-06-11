package com.jualin.apps.data.local.entity

data class Business(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val phone: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
)

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val discount: Double,
    val photoUrl: String,

    val businessId: Int? = 0,
)

data class Service(
    val id: Int,
    val name: String,
    val price: String,
    val discount: Double,

    val businessId: Int? = 0,
)
