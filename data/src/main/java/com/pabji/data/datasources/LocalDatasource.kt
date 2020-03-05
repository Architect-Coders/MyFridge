package com.pabji.data.datasources

import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

interface LocalDatasource {
    suspend fun getProductByBarcode(barcode: String?): Either<DomainError, Product>
    suspend fun getProductById(productId: Long): Either<DomainError, Product>
    suspend fun getProductList(): List<Product>
    suspend fun saveProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun getProductsByTerm(searchTerm: String): List<Product>
}