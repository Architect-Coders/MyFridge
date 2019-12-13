package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName

data class SearchResponse(
        @SerializedName(PRODUCTS) val products: List<ProductResponse> = emptyList()
)