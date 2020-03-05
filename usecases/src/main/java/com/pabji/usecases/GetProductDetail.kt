package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

class GetProductDetail(private val productRepository: ProductRepository) {

    suspend operator fun invoke(product: Product): Either<DomainError, Product> =
        productRepository.getProductDetail(product)
}
