package com.pabji.myfridge.domain.repositories

import com.pabji.myfridge.domain.dtos.ProductDTO

interface SearchRepository {
    suspend fun getRandomProducts(): List<ProductDTO>
}