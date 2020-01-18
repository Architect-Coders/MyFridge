package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class GetProductDetail(private val productRepository: ProductRepository) {

    operator fun invoke(product: Product): Product? = with(productRepository) {
        getProductById(product.id) ?: getProductByBarcode(product.barcode)
    }
}