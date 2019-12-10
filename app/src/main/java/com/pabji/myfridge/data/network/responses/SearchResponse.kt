package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("products") val products: List<ProductResponse> = emptyList()
)