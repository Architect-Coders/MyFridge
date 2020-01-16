package com.pabji.myfridge.presentation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val productId: Long? = null,
    val barcode: String?,
    val name: String?,
    val previewUrl: String?,
    val imageUrl: String?,
    val quantity: String?,
    val stores: List<String>,
    val countries: List<String>,
    val imageNutritionUrl: String?,
    val brands: List<String>,
    val genericName: String?,
    val ingredientsText: String?,
    val imageIngredientsUrl: String?,
    val categories: List<String>,
    val existInFridge: Boolean
) : Parcelable