package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class SaveProduct(private val productRepository: ProductRepository) {

    suspend operator fun invoke(product: Product) = productRepository.saveProduct(product)
}
