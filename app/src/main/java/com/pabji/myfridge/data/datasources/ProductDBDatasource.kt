package com.pabji.myfridge.data.datasources

import androidx.lifecycle.LiveData
import com.pabji.myfridge.domain.dtos.ProductDTO

interface ProductDBDatasource {
    fun getAll(): LiveData<List<ProductDTO>>
    suspend fun insertAll(productList: List<ProductDTO>)
    suspend fun insert(product: ProductDTO)
    suspend fun remove(product: ProductDTO)
    suspend fun getProductById(productId: Long): ProductDTO?
    suspend fun getProductByBarcode(barcode: String): ProductDTO?
    suspend fun getProductsByBarcode(barcodeList: List<String>): List<ProductDTO>
}