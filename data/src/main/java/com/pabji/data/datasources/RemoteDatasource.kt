package com.pabji.data.datasources

import com.pabji.domain.DomainError
import com.pabji.domain.Either
import com.pabji.domain.Product

interface RemoteDatasource {
    suspend fun searchProducts(searchTerm: String?, page: Int): Either<DomainError, List<Product>>
    suspend fun getProductByBarcode(barcode: String): Either<DomainError, Product>
}