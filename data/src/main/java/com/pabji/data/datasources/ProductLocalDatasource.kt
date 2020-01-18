package com.pabji.data.datasources

import com.pabji.domain.Product

interface ProductLocalDatasource {
    fun getProductListByBarcodeList(barcodeList: List<String>): List<Product>
    fun getProductByBarcode(barcode: String): Product?
    fun getProductById(productId: Long): Product?
    fun getProductList(): List<Product>
    fun saveProduct(product: Product)
    fun removeProduct(product: Product)
}