package com.pabji.myfridge

import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.domain.*
import com.pabji.myfridge.di.dataModule
import com.pabji.testshared.mockedLocalProduct
import com.pabji.testshared.mockedLocalProductList
import com.pabji.testshared.mockedProduct
import com.pabji.testshared.mockedRemoteProductList
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

    var isError: Boolean = false

    override suspend fun getProductByBarcode(barcode: String?): Either<DomainError, Product> =
        if (isError) {
            Either.Left(DetailError)
        } else {
            Either.Right(mockedLocalProduct)
        }

    override suspend fun getProductList(): List<Product> = if (isError) {
        emptyList()
    } else {
        mockedLocalProductList
    }

    override suspend fun saveProduct(product: Product) {}

    override suspend fun removeProduct(product: Product) {}

    override suspend fun getProductsByTerm(searchTerm: String): List<Product> = if (isError) {
        emptyList()
    } else {
        mockedLocalProductList
    }
}

class FakeRemoteDataSource : RemoteDatasource {

    var isError: Boolean = false

    override suspend fun searchProducts(searchTerm: String?): Either<DomainError, List<Product>> =
        if (isError) {
            Either.Left(SearchError)
        } else {
            Either.Right(mockedRemoteProductList)
        }

    override suspend fun getProductByBarcode(barcode: String): Either<DomainError, Product> =
        if (isError) {
            Either.Left(DetailError)
        } else {
            Either.Right(mockedProduct)
        }
}
