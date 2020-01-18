package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class GetRandomProducts(private val productRepository: ProductRepository) {

    operator fun invoke(): List<Product> = productRepository.searchProducts()
}