package com.pabji.myfridge.domain.repositories

import androidx.lifecycle.LiveData
import com.pabji.myfridge.domain.dtos.ProductDTO

interface ProductRepository {

    fun getAllProducts(): LiveData<List<ProductDTO>>
    suspend fun insertAll(productList: List<ProductDTO>)
}