package com.pabji.myfridge.ui.productDetail

import com.pabji.domain.Product

data class ProductDetail(
    val id: Long?,
    val barcode: String = "",
    val name: String,
    val previewUrl: String = "",
    val imageUrl: String = "",
    val quantity: String = "",
    val stores: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val imageNutritionUrl: String = "",
    val brands: List<String> = emptyList(),
    val genericName: String = "",
    val ingredientsText: String = "",
    val imageIngredientsUrl: String = "",
    val categories: List<String> = emptyList(),
    val existInFridge: Boolean = false
)

fun Product.toProductDetail(): ProductDetail = ProductDetail(
    id,
    barcode,
    name,
    previewUrl,
    imageUrl,
    quantity,
    stores,
    countries,
    imageNutritionUrl,
    brands,
    genericName,
    ingredientsText,
    imageIngredientsUrl,
    categories,
    existInFridge
)

fun ProductDetail.toProduct(): Product = Product(
    id,
    barcode,
    name,
    previewUrl,
    imageUrl,
    quantity,
    stores,
    countries,
    imageNutritionUrl,
    brands,
    genericName,
    ingredientsText,
    imageIngredientsUrl,
    categories,
    existInFridge
)