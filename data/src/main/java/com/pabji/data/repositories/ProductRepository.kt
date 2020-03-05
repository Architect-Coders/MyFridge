package com.pabji.data.repositories

import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
    suspend fun saveProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun searchProducts(searchTerm: String? = null): List<Product>
    suspend fun getProductDetail(product: Product): Either<DomainError, Product>
    suspend fun searchProductsByBarcode(barcodeList: List<String>): List<Product>
}
