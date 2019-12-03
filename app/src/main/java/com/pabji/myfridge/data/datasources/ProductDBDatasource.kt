package com.pabji.myfridge.data.datasources

import android.app.Application
import com.pabji.myfridge.data.database.MyDatabase
import com.pabji.myfridge.data.extensions.toProductDTOList
import com.pabji.myfridge.data.extensions.toProductEntityList
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductDBDatasource(application: Application) : ProductRepository {

    private val productDao = MyDatabase(application).productDao()

    override suspend fun getAllProducts() = withContext(Dispatchers.IO) { productDao.getAll().toProductDTOList() }
    override suspend fun insertAll(productList: List<ProductDTO>) =
        withContext(Dispatchers.IO) { productDao.insertAll(productList.toProductEntityList()) }
}