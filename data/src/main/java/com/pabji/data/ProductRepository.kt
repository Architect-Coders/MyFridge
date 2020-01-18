package com.pabji.data

import com.pabji.domain.Product

interface ProductRepository {
    fun getProducts(): List<Product>
    fun saveProduct(product: Product)
    fun removeProduct(product: Product)
    fun searchProducts(searchTerm: String?, page: Int): List<Product>
    fun getProductListByBarcodeList(barcodeList: List<String>): List<Product>
    fun getProduct(productId: Long?, barcode: String?): Product?
}