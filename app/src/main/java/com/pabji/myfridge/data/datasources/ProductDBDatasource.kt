package com.pabji.myfridge.data.datasources

import androidx.lifecycle.LiveData
import com.pabji.myfridge.domain.dtos.Product

interface ProductDBDatasource {
    fun getAll(): LiveData<List<Product>>
    suspend fun insertAll(productList: List<Product>)
    suspend fun insert(product: Product)
    suspend fun remove(product: Product)
    suspend fun getProductById(productId: Long): Product?
    suspend fun getProductByBarcode(barcode: String): Product?
    suspend fun getProductsByBarcode(barcodeList: List<String>): List<Product>
}