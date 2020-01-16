package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName
import com.pabji.myfridge.data.network.PRODUCTS

data class SearchResponse(
        @SerializedName(PRODUCTS) val products: List<ProductResponse> = emptyList()
)