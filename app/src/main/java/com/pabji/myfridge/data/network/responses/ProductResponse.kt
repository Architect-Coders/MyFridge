package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName

data class ProductResponse(@SerializedName("product_name") val productName: String = "")