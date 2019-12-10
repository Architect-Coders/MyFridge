package com.pabji.myfridge.domain.repositories

import androidx.lifecycle.LiveData
import com.pabji.myfridge.domain.dtos.ProductDTO

interface ProductRepository {

    fun getAll(): LiveData<List<ProductDTO>>
    suspend fun insertAll(productList: List<ProductDTO>)
    suspend fun insert(product: ProductDTO)
    suspend fun remove(product: ProductDTO)
}