package com.pabji.myfridge.ui.productDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.myfridge.FakeLocalDataSource
import com.pabji.myfridge.FakeRemoteDataSource
import com.pabji.myfridge.initMockedDi
import com.pabji.myfridge.model.toItemProduct
import com.pabji.testshared.mockedLocalProduct
import com.pabji.testshared.mockedProduct
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
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
class ProductDetailIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModelObserver: Observer<ProductDetailViewModel.UiModel>

    private lateinit var remoteDataSource: FakeRemoteDataSource

    private lateinit var localDataSource: FakeLocalDataSource

    private lateinit var vm: ProductDetailViewModel

    private val mockedItemProduct = mockedProduct.toItemProduct()

    @Before
    fun setUp() {
        val vmModule = module {
            factory { ProductDetailViewModel(mockedItemProduct, get(), get(), get()) }
            factory { GetProductDetail(get()) }
            factory { SaveProduct(get()) }
        }

        initMockedDi(vmModule)
        remoteDataSource = get<RemoteDatasource>() as FakeRemoteDataSource
        localDataSource = get<LocalDatasource>() as FakeLocalDataSource
        vm = get()
    }

    @Test
    fun `when local product exist, product is shown`() {
        vm.model.observeForever(uiModelObserver)
        verify(uiModelObserver).onChanged(
            ProductDetailViewModel.UiModel.FullContent(
                mockedLocalProduct
            )
        )
    }

    @Test
    fun `when local product doesnt exist, remote product is shown`() {
        localDataSource.reset()
        vm.model.observeForever(uiModelObserver)
        verify(uiModelObserver).onChanged(ProductDetailViewModel.UiModel.FullContent(mockedProduct))
    }

    @Test
    fun `when local and remote product doesnt exist, error is shown`() {
        remoteDataSource.isError = true
        localDataSource.isError = true
        vm.model.observeForever(uiModelObserver)
        verify(uiModelObserver).onChanged(ProductDetailViewModel.UiModel.Error)
    }

    @Test
    fun `when save product, product saved state is shown`() {
        localDataSource.reset()
        vm.model.observeForever(uiModelObserver)
        vm.onClickButtonAdd()
        verify(uiModelObserver).onChanged(ProductDetailViewModel.UiModel.ProductSaved(mockedProduct))
    }
}
