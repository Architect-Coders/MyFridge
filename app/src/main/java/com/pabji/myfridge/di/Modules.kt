package com.pabji.myfridge.di

import android.app.Application
import androidx.room.Room
import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.data.repositories.ProductRepository
import com.pabji.data.repositories.ProductRepositoryImpl
import com.pabji.myfridge.model.database.RoomDataSource
import com.pabji.myfridge.model.database.RoomDatabase
import com.pabji.myfridge.model.network.RetrofitDataSource
import com.pabji.myfridge.model.network.api.RetrofitApiClient
import com.pabji.myfridge.ui.common.uiModels.ItemProductList
import com.pabji.myfridge.ui.newProduct.NewProductActivity
import com.pabji.myfridge.ui.newProduct.NewProductViewModel
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity
import com.pabji.myfridge.ui.productDetail.ProductDetailViewModel
import com.pabji.myfridge.ui.productList.ProductListFragment
import com.pabji.myfridge.ui.productList.ProductListViewModel
import com.pabji.myfridge.ui.searchProducts.SearchProductsFragment
import com.pabji.myfridge.ui.searchProducts.SearchProductsViewModel
import com.pabji.usecases.GetMyProducts
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
import com.pabji.usecases.SearchProductByTerm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

val appModule = module {
    single {
        Room.databaseBuilder(get(), RoomDatabase::class.java, "products.db")
            .fallbackToDestructiveMigration().build()
    }
    single { RetrofitApiClient.createService() }
    factory<LocalDatasource> { RoomDataSource(get()) }
    factory<RemoteDatasource> { RetrofitDataSource(get()) }
}

val dataModule = module {
    factory<ProductRepository> { ProductRepositoryImpl(get(), get()) }
}

val scopesModule = module {

    scope(named<ProductListFragment>()) {
        viewModel { ProductListViewModel(get()) }
        scoped { GetMyProducts(get()) }
    }

    scope(named<SearchProductsFragment>()) {
        viewModel { SearchProductsViewModel(get()) }
        scoped { SearchProductByTerm(get()) }
    }

    scope(named<NewProductActivity>()) {
        viewModel { NewProductViewModel(get()) }
        scoped { SaveProduct(get()) }
    }

    scope(named<ProductDetailActivity>()) {
        viewModel { (product: ItemProductList) -> ProductDetailViewModel(product, get(), get()) }
        scoped { GetProductDetail(get()) }
        scoped { SaveProduct(get()) }
    }
}