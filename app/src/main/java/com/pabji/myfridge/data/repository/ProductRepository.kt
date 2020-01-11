package com.pabji.myfridge.data.repository

import androidx.lifecycle.LiveData
import com.pabji.myfridge.domain.dtos.ProductDTO

interface ProductRepository {
    fun getAll(): LiveData<List<ProductDTO>>
    suspend fun insertAll(productList: List<ProductDTO>)
    suspend fun insert(product: ProductDTO)
    suspend fun remove(product: ProductDTO)
    suspend fun searchProducts(searchTerm: String? = null, page: Int = 1): List<ProductDTO>
    suspend fun getProductDetailByBarcode(barcode: String): ProductDTO?
    suspend fun getProductById(productId: Long): ProductDTO?
}