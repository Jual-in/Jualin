package com.jualin.apps.data.local.entity

data class User(
    val isLogin: Boolean,

    val id: Int,
    var name: String? = null,
    var email: String? = null,
    var role: String? = null,
    var alamat: String? = null,
    var photoUrl: String? = null,
)
