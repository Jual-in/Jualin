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
    val photoUrl: String
)

data class Service(
    val id: Int,
    val name: String,
    val price: String,
    val discount: Double,
)
