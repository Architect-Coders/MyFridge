package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class SearchProductsByTerm(private val productRepository: ProductRepository) {

    suspend operator fun invoke(
        searchTerm: String
    ): List<Product> = productRepository.searchProducts(searchTerm)

}