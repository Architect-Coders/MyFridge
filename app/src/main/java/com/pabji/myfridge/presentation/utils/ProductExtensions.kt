package com.pabji.myfridge.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.presentation.models.Product

internal fun ProductDTO.toProduct() =
    Product(id, name)
internal fun List<ProductDTO>.toProductList() = map { it.toProduct() }
internal fun LiveData<List<ProductDTO>>.toProductListLiveData() =
    Transformations.map(this) { it.toProductList() }

internal fun Product.toProductDTO() = ProductDTO(id, name)
internal fun List<Product>.toProductDTOList() = map { it.toProductDTO() }
