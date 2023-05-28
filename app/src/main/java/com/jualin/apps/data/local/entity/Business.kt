package com.jualin.apps.data.local.entity

data class Business(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
)
