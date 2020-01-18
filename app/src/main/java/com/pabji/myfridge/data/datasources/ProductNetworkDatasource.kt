package com.pabji.myfridge.data.datasources

import com.pabji.myfridge.domain.dtos.Product

interface ProductNetworkDatasource {
    suspend fun searchProducts(searchTerm: String? = null, page: Int = 1): List<Product>
    suspend fun getProductByBarcode(barcode: String): Product?
}