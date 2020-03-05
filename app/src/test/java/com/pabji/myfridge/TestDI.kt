package com.pabji.myfridge

import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.domain.*
import com.pabji.myfridge.di.dataModule
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single<LocalDatasource> { FakeLocalDataSource() }
    single<RemoteDatasource> { FakeRemoteDataSource() }
    single { Dispatchers.Unconfined }
}

class FakeLocalDataSource : LocalDatasource {

    var productList: MutableList<Product> = mutableListOf()
    var isError: Boolean = false

    override suspend fun getProductByBarcode(barcode: String?): Either<DomainError, Product> =
        if (isError) {
            Either.Left(DetailError)
        } else {
            Either.Right(productList.first { it.barcode == barcode })
        }

    override suspend fun getProductById(productId: Long): Either<DomainError, Product> =
        if (isError) {
            Either.Left(DetailError)
        } else {
            Either.Right(productList.first { it.id == productId })
        }

    override suspend fun getProductList(): List<Product> = productList

    override suspend fun saveProduct(product: Product) {
        productList.add(product)
    }

    override suspend fun removeProduct(product: Product) {
        productList.remove(product)
    }

    override suspend fun getProductsByTerm(searchTerm: String): List<Product> =
        productList.filterIndexed { _, product ->
            product.name.startsWith(searchTerm)
        }
}

class FakeRemoteDataSource : RemoteDatasource {

    var productList: MutableList<Product> = mutableListOf()
    var isError: Boolean = false

    override suspend fun searchProducts(searchTerm: String?): Either<DomainError, List<Product>> =
        if (isError) {
            Either.Left(SearchError)
        } else {
            Either.Right(productList.filterIndexed { _, product ->
                product.name.startsWith(searchTerm ?: "")
            })
        }

    override suspend fun getProductByBarcode(barcode: String): Either<DomainError, Product> =
        if (isError) {
            Either.Left(DetailError)
        } else {
            Either.Right(productList.first { it.barcode == barcode })
        }
}