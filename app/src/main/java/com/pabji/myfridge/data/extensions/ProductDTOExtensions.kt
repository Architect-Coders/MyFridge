package com.pabji.myfridge.data.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import arrow.core.toOption
import com.pabji.myfridge.data.database.entities.ProductEntity
import com.pabji.myfridge.data.network.responses.ProductResponse
import com.pabji.myfridge.data.network.responses.SearchResponse
import com.pabji.myfridge.domain.dtos.ProductDTO

internal fun ProductEntity.toProductDTO() =
    ProductDTO(id.toOption(), name.toOption(), previewUrl.toOption())
internal fun List<ProductEntity>.fromEntityToProductDTOList() = map { it.toProductDTO() }
internal fun LiveData<List<ProductEntity>>.toProductDTOListLiveData() =
    Transformations.map(this) { it.fromEntityToProductDTOList() }

internal fun ProductDTO.toProductEntity() =
    ProductEntity(id.orNull(), name.orNull(), previewUrl.orNull())
internal fun List<ProductDTO>.toProductEntityList() = map { it.toProductEntity() }

internal fun SearchResponse.toProductDTOList() = products.fromResponseToProductDTOList()
internal fun List<ProductResponse>.fromResponseToProductDTOList() = map { it.toProductDTO() }
internal fun ProductResponse.toProductDTO() =
    ProductDTO(name = productName.toOption(), previewUrl = previewUrl.toOption())