package com.pabji.domain

data class Product(
    val id: Long? = null,
    val barcode: String = "",
    val name: String,
    val previewUrl: String = "",
    val imageUrl: String = "",
    val quantity: String = "",
    val stores: String = "",
    val countries: List<String> = emptyList(),
    val imageNutritionUrl: String = "",
    val brands: List<String> = emptyList(),
    val genericName: String = "",
    val ingredientsText: String = "",
    val imageIngredientsUrl: String = "",
    val categories: String = "",
    val existInFridge: Boolean = false
)
