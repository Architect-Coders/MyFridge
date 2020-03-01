package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class GetMyProducts(private val productRepository: ProductRepository) {

    suspend operator fun invoke(): List<Product> = productRepository.getProducts()
}