package com.pabji.myfridge.ui.extensions

import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.ui.productList.Product

internal fun ProductDTO.toProduct() = Product(name)
internal fun List<ProductDTO>.toProductList() = map { it.toProduct() }

internal fun Product.toProductDTO() = ProductDTO(name)
internal fun List<Product>.toProductDTOList() = map { it.toProductDTO() }