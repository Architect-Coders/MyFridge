package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName
import com.pabji.myfridge.data.network.PRODUCT

data class ProductDetailResponse(
    @SerializedName(PRODUCT) val product: ProductResponse?
)