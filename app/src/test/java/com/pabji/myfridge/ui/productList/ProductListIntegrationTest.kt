package com.pabji.myfridge.ui.productList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.datasources.LocalDatasource
import com.pabji.myfridge.FakeLocalDataSource
import com.pabji.myfridge.initMockedDi
import com.pabji.myfridge.model.toItemProduct
import com.pabji.testshared.mockedLocalProductList
import com.pabji.usecases.GetMyProducts
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
class ProductListIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModelObserver: Observer<ProductListViewModel.UiModel>

    private lateinit var localDataSource: FakeLocalDataSource

    private lateinit var vm: ProductListViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { ProductListViewModel(get(), get()) }
            factory { GetMyProducts(get()) }
        }

        initMockedDi(vmModule)
        localDataSource = get<LocalDatasource>() as FakeLocalDataSource
        vm = get()
    }

    @Test
    fun `when local is empty, should show empty product list state`() {
        vm.model.observeForever(uiModelObserver)
        vm.updateData()
        verify(uiModelObserver).onChanged(ProductListViewModel.UiModel.EmptyList)
    }

    @Test
    fun `when local have products, should show product list state`() {
        vm.model.observeForever(uiModelObserver)
        localDataSource.productList = mockedLocalProductList.toMutableList()
        vm.updateData()
        verify(uiModelObserver).onChanged(
            ProductListViewModel.UiModel.Content(
                mockedLocalProductList.map { it.toItemProduct() })
        )
    }
}
