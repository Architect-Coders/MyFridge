package com.pabji.myfridge.data.network.responses

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("product_name") val productName: String?,
    @SerializedName("image_small_url") val previewUrl: String?,
    @SerializedName("quantity") val quantity: String?,
    @SerializedName("code") val barcode: String?,
    @SerializedName("stores") val stores: String?,
    @SerializedName("countries") val countries: String?,
    @SerializedName("image_nutrition_url") val imageNutritionUrl: String?,
    @SerializedName("generic_name") val genericName: String?,
    @SerializedName("ingredients_text") val ingredientsText: String?,
    @SerializedName("image_ingredients_url") val ingredientsUrl: String?,
    @SerializedName("categories") val categories: String?
)