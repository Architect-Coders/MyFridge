package com.pabji.domain

data class Product(
    val id: Long,
    val barcode: String,
    val name: String,
    val previewUrl: String,
    val imageUrl: String,
    val quantity: String,
    val stores: List<String>,
    val countries: List<String>,
    val imageNutritionUrl: String,
    val brands: List<String>,
    val genericName: String,
    val ingredientsText: String,
    val imageIngredientsUrl: String,
    val categories: List<String>,
    var existInFridge: Boolean
)