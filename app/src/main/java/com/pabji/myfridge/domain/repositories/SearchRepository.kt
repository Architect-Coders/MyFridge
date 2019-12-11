package com.pabji.myfridge.domain.repositories

import arrow.core.Either
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.domain.errors.DomainError

interface SearchRepository {
    suspend fun searchProductsByName(
        searchTerm: String,
        page: Int = 1
    ): Either<DomainError, List<ProductDTO>>
}