package com.pabji.myfridge.data.repository

import androidx.lifecycle.LiveData
import com.pabji.myfridge.domain.dtos.Product

interface ProductRepository {
    fun getAll(): LiveData<List<Product>>
    suspend fun insertAll(productList: List<Product>)
    suspend fun insert(product: Product)
    suspend fun remove(product: Product)
    suspend fun searchProducts(searchTerm: String? = null, page: Int = 1): List<Product>
    suspend fun getProductDetailByBarcode(barcode: String): Product?
    suspend fun getProductById(productId: Long): Product?
}