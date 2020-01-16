package com.pabji.myfridge.data.datasources

import com.pabji.myfridge.domain.dtos.ProductDTO

interface ProductNetworkDatasource {
    suspend fun searchProducts(searchTerm: String? = null, page: Int = 1): List<ProductDTO>
    suspend fun getProductByBarcode(barcode: String): ProductDTO?
}