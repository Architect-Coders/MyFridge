package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName
import com.pabji.myfridge.data.network.*

data class ProductResponse(
    @SerializedName(PRODUCT_NAME) val productName: String?,
    @SerializedName(IMAGE_SMALL_URL) val previewUrl: String?,
    @SerializedName(QUANTITY) val quantity: String?,
    @SerializedName(CODE) val barcode: String?,
    @SerializedName(STORES) val stores: String?,
    @SerializedName(COUNTRIES) val countries: String?,
    @SerializedName(IMAGE_NUTRITION_URL) val imageNutritionUrl: String?,
    @SerializedName(BRANDS) val brands: String?,
    @SerializedName(GENERIC_NAME) val genericName: String?,
    @SerializedName(INGREDIENTS_TEXT) val ingredientsText: String?,
    @SerializedName(IMAGE_INGREDIENTS_URL) val ingredientsUrl: String?,
    @SerializedName(CATEGORIES) val categories: String?
)