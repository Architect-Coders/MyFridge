package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

class GetProductDetail(private val productRepository: ProductRepository) {

    suspend operator fun invoke(product: Product): Either<DomainError, Product> =
        with(productRepository.getProductById(product.id ?: 0)) {
            if (isLeft) {
                productRepository.getProductByBarcode(product.barcode)
            } else {
                this
            }
        }
}