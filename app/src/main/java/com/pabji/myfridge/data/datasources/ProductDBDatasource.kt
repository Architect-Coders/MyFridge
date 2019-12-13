package com.pabji.myfridge.data.datasources

import com.pabji.myfridge.MyApp
import com.pabji.myfridge.data.utils.toProductDTOListLiveData
import com.pabji.myfridge.data.utils.toProductEntity
import com.pabji.myfridge.data.utils.toProductEntityList
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductDBDatasource(application: MyApp) : ProductRepository {

    private val productDao = application.db.productDao()

    override fun getAll() = productDao.getAll().toProductDTOListLiveData()
    override suspend fun insertAll(productList: List<ProductDTO>) =
        withContext(Dispatchers.IO) { productDao.insertAll(productList.toProductEntityList()) }

    override suspend fun insert(product: ProductDTO) =
        withContext(Dispatchers.IO) { productDao.insert(product.toProductEntity()) }

    override suspend fun remove(product: ProductDTO) =
        withContext(Dispatchers.IO) { productDao.remove(product.toProductEntity()) }
}