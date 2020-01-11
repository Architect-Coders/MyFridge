package com.pabji.myfridge.data.datasources

import com.pabji.myfridge.MyApp
import com.pabji.myfridge.data.utils.toProductDTO
import com.pabji.myfridge.data.utils.toProductDTOListLiveData
import com.pabji.myfridge.data.utils.toProductEntity
import com.pabji.myfridge.data.utils.toProductEntityList
import com.pabji.myfridge.domain.dtos.ProductDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductDBDatasourceImpl(application: MyApp) : ProductDBDatasource {

    private val productDao = application.db.productDao()

    override fun getAll() = productDao.getAll().toProductDTOListLiveData()

    override suspend fun getProductById(productId: Long) =
        withContext(Dispatchers.IO) { productDao.getProductById(productId)?.toProductDTO() }

    override suspend fun insertAll(productList: List<ProductDTO>) =
        withContext(Dispatchers.IO) { productDao.insertAll(productList.toProductEntityList()) }

    override suspend fun insert(product: ProductDTO) =
        withContext(Dispatchers.IO) { productDao.insert(product.toProductEntity()) }

    override suspend fun remove(product: ProductDTO) =
        withContext(Dispatchers.IO) { productDao.remove(product.toProductEntity()) }

    override suspend fun getProductByBarcode(barcode: String): ProductDTO? =
        withContext(Dispatchers.IO) { productDao.getProductByBarcode(barcode)?.toProductDTO() }

    override suspend fun getProductsByBarcode(barcodeList: List<String>) =
        withContext(Dispatchers.IO) {
            productDao.getProductsByBarcode(barcodeList).map { entity -> entity.toProductDTO() }
        }
}