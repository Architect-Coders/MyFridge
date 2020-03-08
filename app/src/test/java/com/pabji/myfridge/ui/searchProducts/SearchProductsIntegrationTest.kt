package com.pabji.myfridge.ui.searchProducts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.myfridge.FakeLocalDataSource
import com.pabji.myfridge.FakeRemoteDataSource
import com.pabji.myfridge.initMockedDi
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.searchProducts.SearchProductsViewModel.UiModel
import com.pabji.testshared.mockedFilteredProducts
import com.pabji.testshared.mockedLocalProductList
import com.pabji.testshared.mockedRemoteProductList
import com.pabji.usecases.SearchProductsByTerm
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchProductsIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModelObserver: Observer<UiModel>

    private lateinit var remoteDataSource: FakeRemoteDataSource

    private lateinit var localDataSource: FakeLocalDataSource

    private lateinit var vm: SearchProductsViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { SearchProductsViewModel(get(), get()) }
            factory { SearchProductsByTerm(get()) }
        }

        initMockedDi(vmModule)
        remoteDataSource = get<RemoteDatasource>() as FakeRemoteDataSource
        localDataSource = get<LocalDatasource>() as FakeLocalDataSource
        vm = get()
    }

    @Test
    fun `when init, remote random list should be shown`() {
        remoteDataSource.productList = mockedRemoteProductList.toMutableList()
        vm.model.observeForever(uiModelObserver)
        verify(uiModelObserver)
            .onChanged(UiModel.Content(mockedRemoteProductList.map { it.toItemProduct() }))
    }

    @Test
    fun `when empty term sended, empty list should be shown`() {
        vm.model.observeForever(uiModelObserver)
        vm.onSearch("")
        verify(uiModelObserver, times(2)).onChanged(UiModel.EmptyList)
    }

    @Test
    fun `with searh term, product list should be shown`() {
        remoteDataSource.productList = mockedRemoteProductList.toMutableList()
        localDataSource.productList = mockedLocalProductList.toMutableList()
        vm.model.observeForever(uiModelObserver)
        vm.onSearch("Product")
        verify(uiModelObserver, times(2))
            .onChanged(UiModel.Content(mockedFilteredProducts.map { it.toItemProduct() }))
    }

    @Test
    fun `with searh term and remote returns error, empty product list should be shown`() {
        remoteDataSource.isError = true
        vm.model.observeForever(uiModelObserver)
        vm.onSearch("Product")
        verify(uiModelObserver, times(2)).onChanged(UiModel.EmptyList)
    }

    @Test
    fun `with searh term, remote returns error and local have products, local product list should be shown`() {
        remoteDataSource.isError = true
        localDataSource.productList = mockedLocalProductList.toMutableList()
        vm.model.observeForever(uiModelObserver)
        vm.onSearch("Product")
        verify(uiModelObserver, times(2))
            .onChanged(UiModel.Content(mockedLocalProductList.map { it.toItemProduct() }))
    }
}
