package com.pabji.myfridge.presentation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.presentation.models.Product

internal fun ProductDTO.toProduct() =
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
internal fun List<ProductDTO>.toProductList() = map { it.toProduct() }
internal fun LiveData<List<ProductDTO>>.toProductListLiveData() =
    Transformations.map(this) { it.toProductList() }

internal fun LiveData<ProductDTO>.toProductLiveData() =
    Transformations.map(this) { it.toProduct() }

internal fun Product.toProductDTO() =
    ProductDTO(
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