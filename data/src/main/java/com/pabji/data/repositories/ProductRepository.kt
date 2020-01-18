package com.pabji.data.repositories

import com.pabji.domain.Product

interface ProductRepository {
    fun getProducts(): List<Product>
    fun saveProduct(product: Product)
    fun removeProduct(product: Product)
    fun searchProducts(searchTerm: String? = null, page: Int = 1): List<Product>
    fun getProductListByBarcodeList(barcodeList: List<String>): List<Product>
    fun getProductById(productId: Long): Product?
    fun getProductByBarcode(barcode: String): Product?
}