package com.pabji.myfridge.ui.barcode

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.myfridge.FakeRemoteDataSource
import com.pabji.myfridge.initMockedDi
import com.pabji.myfridge.model.toItemProduct
import com.pabji.testshared.mockedBarcodeList
import com.pabji.testshared.mockedRemoteProductList
import com.pabji.usecases.SearchProductsByBarcode
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
class BarcodeReaderIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var uiModelObserver: Observer<BarcodeReaderViewModel.UiModel>

    private lateinit var vm: BarcodeReaderViewModel

    private lateinit var remoteDataSource: FakeRemoteDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { BarcodeReaderViewModel(get(), get()) }
            factory { SearchProductsByBarcode(get()) }
        }

        initMockedDi(vmModule)
        remoteDataSource = get<RemoteDatasource>() as FakeRemoteDataSource
        vm = get()
    }

    @Test
    fun `when barcode list detected, product list is shown`() {
        vm.model.observeForever(uiModelObserver)
        vm.onBarcodeDetected(mockedBarcodeList)
        verify(uiModelObserver).onChanged(
            BarcodeReaderViewModel.UiModel.Content(mockedRemoteProductList.map { it.toItemProduct() })
        )
    }

    @Test
    fun `when barcode list detected, error is shown`() {
        remoteDataSource.isError = true
        vm.model.observeForever(uiModelObserver)
        vm.onBarcodeDetected(mockedBarcodeList)
        verify(uiModelObserver).onChanged(
            BarcodeReaderViewModel.UiModel.Content(emptyList())
        )
    }
}
