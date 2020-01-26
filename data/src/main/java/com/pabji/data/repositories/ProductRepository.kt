package com.pabji.data.repositories

import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun saveProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun searchProducts(searchTerm: String? = null): List<Product>
    suspend fun getProductById(productId: Long): Either<DomainError, Product>
    suspend fun getProductByBarcode(barcode: String): Either<DomainError, Product>
}