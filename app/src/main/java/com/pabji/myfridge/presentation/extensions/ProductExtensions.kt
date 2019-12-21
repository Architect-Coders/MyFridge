package com.pabji.myfridge.presentation.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import arrow.core.getOrElse
import arrow.core.toOption
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.presentation.models.Product

internal fun ProductDTO.toProduct() =
    Product(id.orNull(), name.getOrElse { "" }, previewUrl.getOrElse { "" })
internal fun List<ProductDTO>.toProductList() = map { it.toProduct() }
internal fun LiveData<List<ProductDTO>>.toProductListLiveData() =
    Transformations.map(this) { it.toProductList() }

internal fun Product.toProductDTO() =
    ProductDTO(id.toOption(), name.toOption(), previewUrl.toOption())