package com.pabji.myfridge.data.extensions

import com.pabji.myfridge.data.database.entities.ProductEntity
import com.pabji.myfridge.domain.dtos.ProductDTO

internal fun ProductEntity.toProductDTO() = ProductDTO(name)
internal fun List<ProductEntity>.toProductDTOList() = map { it.toProductDTO() }

internal fun ProductDTO.toProductEntity() = ProductEntity(name = name)
internal fun List<ProductDTO>.toProductEntityList() = map { it.toProductEntity() }