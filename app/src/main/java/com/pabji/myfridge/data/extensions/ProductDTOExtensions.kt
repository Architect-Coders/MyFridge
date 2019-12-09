package com.pabji.myfridge.data.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.pabji.myfridge.data.database.entities.ProductEntity
import com.pabji.myfridge.domain.dtos.ProductDTO

internal fun ProductEntity.toProductDTO() = ProductDTO(id, name)
internal fun List<ProductEntity>.toProductDTOList() = map { it.toProductDTO() }
internal fun LiveData<List<ProductEntity>>.toProductDTOListLiveData() =
    Transformations.map(this) { it.toProductDTOList() }

internal fun ProductDTO.toProductEntity() = ProductEntity(id, name)
internal fun List<ProductDTO>.toProductEntityList() = map { it.toProductEntity() }