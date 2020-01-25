package com.pabji.myfridge.model.network.responses

import com.google.gson.annotations.SerializedName
import com.pabji.myfridge.model.network.api.PRODUCT

data class ProductDetailResponse(
    @SerializedName(PRODUCT) val product: ProductResponse?
)