package com.jualin.apps.data.remote.response.product

import com.google.gson.annotations.SerializedName
import com.jualin.apps.data.remote.response.search.ProductResponse

data class AddProductResponse(
    @field:SerializedName("product")
    val product: ProductResponse
)