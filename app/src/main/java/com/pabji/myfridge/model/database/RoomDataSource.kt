package com.pabji.myfridge.model.database

import com.pabji.data.datasources.LocalDatasource
import com.pabji.domain.DetailError
import com.pabji.domain.Either
import com.pabji.domain.Product
import com.pabji.domain.SearchError
import com.pabji.myfridge.model.database.entities.toProduct
import com.pabji.myfridge.model.database.entities.toProductEntity

class RoomDataSource(database: RoomDatabase) : LocalDatasource {

    private val productDao = database.productDao()

    override suspend fun getProductListByBarcodeList(barcodeList: List<String>) =
        Either.Right(productDao.getProductsByBarcode(barcodeList).map { it.toProduct() })

    override suspend fun getProductByBarcode(barcode: String) =
        productDao.getProductByBarcode(barcode)?.run {
            Either.Right(toProduct())
        } ?: run {
            Either.Left(DetailError)
        }

    override suspend fun getProductById(productId: Long) =
        productDao.getProductById(productId)?.run {
            Either.Right(toProduct())
        } ?: run {
            Either.Left(DetailError)
        }

    override suspend fun getProductList() = Either.Right(productDao.getAll().map { it.toProduct() })

    override suspend fun saveProduct(product: Product) =
        productDao.insert(product.toProductEntity())

    override suspend fun removeProduct(product: Product) =
        productDao.remove(product.toProductEntity())

    override suspend fun getProductsByTerm(searchTerm: String?) =
        searchTerm?.run {
            Either.Right(productDao.getProductsByTerm(searchTerm).map { it.toProduct() })
        } ?: run {
            Either.Left(SearchError)
        }
}