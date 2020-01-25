package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

class SearchProductByTerm(private val productRepository: ProductRepository) {

    suspend operator fun invoke(
        searchTerm: String,
        page: Int = 1
    ): Either<DomainError, List<Product>> = productRepository.searchProducts(searchTerm, page)

}