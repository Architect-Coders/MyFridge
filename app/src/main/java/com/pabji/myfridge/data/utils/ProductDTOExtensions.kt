package com.pabji.myfridge.data.utils

import com.pabji.myfridge.common.extensions.getListByDelimit
import com.pabji.myfridge.data.network.responses.ProductDetailResponse
import com.pabji.myfridge.data.network.responses.ProductResponse
import com.pabji.myfridge.data.network.responses.SearchResponse
import com.pabji.myfridge.domain.dtos.Product

internal fun SearchResponse.toProductDTOList() = products.map { it.toProductDTO() }
internal fun ProductDetailResponse.toProductDTO() = product?.run {
    toProductDTO()
} ?: run {
    Product()
}

internal fun ProductResponse.toProductDTO() =
    Product(
        barcode = barcode,
        name = productName,
        previewUrl = previewUrl,
        imageUrl = imageUrl,
        quantity = quantity,
        stores = stores?.getListByDelimit(",") ?: emptyList(),
        countries = countries?.getListByDelimit(",") ?: emptyList(),
        imageNutritionUrl = imageNutritionUrl,
        brands = brands?.getListByDelimit(",") ?: emptyList(),
        genericName = genericName,
        ingredientsText = ingredientsText,
        imageIngredientsUrl = ingredientsUrl,
        categories = categories?.getListByDelimit(",") ?: emptyList()
    )