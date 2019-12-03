package com.pabji.myfridge.domain.repositories

import com.pabji.myfridge.domain.dtos.ProductDTO

interface ProductRepository {

    suspend fun getAllProducts(): List<ProductDTO>
    suspend fun insertAll(productList: List<ProductDTO>)
}