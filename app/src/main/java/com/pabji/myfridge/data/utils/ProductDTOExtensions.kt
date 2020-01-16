package com.pabji.myfridge.data.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pabji.myfridge.data.database.entities.ProductEntity
import com.pabji.myfridge.data.network.responses.ProductDetailResponse
import com.pabji.myfridge.data.network.responses.ProductResponse
import com.pabji.myfridge.data.network.responses.SearchResponse
import com.pabji.myfridge.domain.dtos.ProductDTO

internal fun ProductEntity.toProductDTO() =
    ProductDTO(
        id,
        barcode,
        name,
        previewUrl,
        imageUrl,
        quantity,
        stores.getListByDelimit(","),
        countries.getListByDelimit(","),
        imageNutritionUrl,
        brands.getListByDelimit(","),
        genericName,
        ingredientsText,
        imageIngredientsUrl,
        categories.getListByDelimit(","),
        true
    )

internal fun LiveData<List<ProductEntity>>.toProductDTOListLiveData() =
    Transformations.map(this) { it.map { entity -> entity.toProductDTO() } }

internal fun ProductDTO.toProductEntity() = ProductEntity(
    id,
    barcode,
    name,
    previewUrl,
    imageUrl,
    quantity,
    stores.joinToString(),
    countries.joinToString(),
    imageNutritionUrl,
    brands.joinToString(),
    genericName,
    ingredientsText,
    imageIngredientsUrl,
    categories.joinToString()
)

internal fun List<ProductDTO>.toProductEntityList() = map { it.toProductEntity() }

internal fun SearchResponse.toProductDTOList() = products.map { it.toProductDTO() }
internal fun ProductDetailResponse.toProductDTO() = product?.run {
    toProductDTO()
} ?: run {
    ProductDTO()
}

internal fun ProductResponse.toProductDTO() =
    ProductDTO(
        barcode = barcode,
        name = productName,
        previewUrl = previewUrl,
        imageUrl = imageUrl,
        quantity = quantity,
        stores = stores.getListByDelimit(","),
        countries = countries.getListByDelimit(","),
        imageNutritionUrl = imageNutritionUrl,
        brands = brands.getListByDelimit(","),
        genericName = genericName,
        ingredientsText = ingredientsText,
        imageIngredientsUrl = ingredientsUrl,
        categories = categories.getListByDelimit(",")
    )

private fun String?.getListByDelimit(delimit: String) =
    this?.run { split(delimit).map { it.trim() } } ?: emptyList()