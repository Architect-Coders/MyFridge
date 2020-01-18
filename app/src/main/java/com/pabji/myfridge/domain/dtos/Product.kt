package com.pabji.myfridge.domain.dtos

data class Product(
    val id: Long? = null,
    val barcode: String? = null,
    val name: String? = null,
    val previewUrl: String? = null,
    val imageUrl: String? = null,
    val quantity: String? = null,
    val stores: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val imageNutritionUrl: String? = null,
    val brands: List<String> = emptyList(),
    val genericName: String? = null,
    val ingredientsText: String? = null,
    val imageIngredientsUrl: String? = null,
    val categories: List<String> = emptyList(),
    var existInFridge: Boolean = false
)