package com.pabji.myfridge.model.network.responses

import com.google.gson.annotations.SerializedName
import com.pabji.domain.Product
import com.pabji.myfridge.model.common.extensions.getListByDelimit
import com.pabji.myfridge.model.network.api.*

data class ProductResponse(
    @SerializedName(PRODUCT_NAME) val productName: String?,
    @SerializedName(IMAGE_SMALL_URL) val previewUrl: String?,
    @SerializedName(IMAGE_FRONT_URL) val imageUrl: String?,
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

internal fun ProductResponse.toProduct() =
    Product(
        barcode = barcode ?: "",
        name = productName ?: "",
        previewUrl = previewUrl ?: "",
        imageUrl = imageUrl ?: "",
        quantity = quantity ?: "",
        stores = stores?.getListByDelimit(",") ?: emptyList(),
        countries = countries?.getListByDelimit(",") ?: emptyList(),
        imageNutritionUrl = imageNutritionUrl ?: "",
        brands = brands?.getListByDelimit(",") ?: emptyList(),
        genericName = genericName ?: "",
        ingredientsText = ingredientsText ?: "",
        imageIngredientsUrl = ingredientsUrl ?: "",
        categories = categories?.getListByDelimit(",") ?: emptyList()
    )