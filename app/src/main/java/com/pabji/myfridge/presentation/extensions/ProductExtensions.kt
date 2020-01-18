package com.pabji.myfridge.presentation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

internal fun com.pabji.myfridge.domain.dtos.Product.toProduct() =
    Product(
        productId = id,
        barcode = barcode,
        name = name ?: "",
        quantity = quantity ?: "",
        stores = stores,
        countries = countries,
        imageNutritionUrl = imageNutritionUrl ?: "",
        brands = brands,
        genericName = genericName ?: "",
        ingredientsText = ingredientsText ?: "",
        imageIngredientsUrl = imageIngredientsUrl ?: "",
        categories = categories,
        imageUrl = imageUrl ?: "",
        previewUrl = previewUrl ?: "",
        existInFridge = existInFridge
    )

internal fun List<com.pabji.myfridge.domain.dtos.Product>.toProductList() = map { it.toProduct() }
internal fun LiveData<List<com.pabji.myfridge.domain.dtos.Product>>.toProductListLiveData() =
    Transformations.map(this) { it.toProductList() }

internal fun LiveData<com.pabji.myfridge.domain.dtos.Product>.toProductLiveData() =
    Transformations.map(this) { it.toProduct() }

internal fun Product.toProductDTO() =
    com.pabji.myfridge.domain.dtos.Product(
        productId,
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