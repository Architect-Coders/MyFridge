package com.pabji.myfridge.data.datasources

import android.app.Application
import com.pabji.myfridge.data.database.MyDatabase
import com.pabji.myfridge.data.extensions.toProductDTOListLiveData
import com.pabji.myfridge.data.extensions.toProductEntityList
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductDBDatasource(application: Application) : ProductRepository {

    private val productDao = MyDatabase(application).productDao()

    override fun getAllProducts() = productDao.getAll().toProductDTOListLiveData()
    override suspend fun insertAll(productList: List<ProductDTO>) =
        withContext(Dispatchers.IO) { productDao.insertAll(productList.toProductEntityList()) }
}