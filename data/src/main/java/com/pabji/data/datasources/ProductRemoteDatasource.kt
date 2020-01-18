package com.pabji.data.datasources

import com.pabji.domain.Product

interface ProductRemoteDatasource {
    fun searchProducts(searchTerm: String?, page: Int): List<Product>
    fun getProductByBarcode(barcode: String): Product?
}