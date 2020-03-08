package com.pabji.myfridge.model.network.responses

import com.google.gson.annotations.SerializedName
import com.pabji.myfridge.model.network.api.PRODUCTS

data class SearchResponse(
    @SerializedName(PRODUCTS) val products: List<ProductResponse> = emptyList()
)
