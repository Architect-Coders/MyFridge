package com.pabji.myfridge.ui.barcode

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.Event
import com.pabji.testshared.mockedBarcodeList
import com.pabji.testshared.mockedProduct
import com.pabji.testshared.mockedRemoteProductList
import com.pabji.usecases.SearchProductsByBarcode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BarcodeReaderViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchProductsByBarcode: SearchProductsByBarcode

    @Mock
    lateinit var uiModelObserver: Observer<BarcodeReaderViewModel.UiModel>

    @Mock
    lateinit var uiNavigationObserver: Observer<Event<ItemProduct>>

    private lateinit var vm: BarcodeReaderViewModel

    @Before
    fun setUp() {
        vm = BarcodeReaderViewModel(searchProductsByBarcode, Dispatchers.Unconfined)
    }

    @Test
    fun `while observing Model LiveData and refresh, request camera permission`() {
        runBlocking {
            vm.model.observeForever(uiModelObserver)
            vm.refresh()
            verify(uiModelObserver).onChanged(BarcodeReaderViewModel.UiModel.RequestCameraPermission)
        }
    }

    @Test
    fun `after request camera permission and permission granted, start camera`() {
        runBlocking {
            vm.model.observeForever(uiModelObserver)
            vm.onCameraPermissionRequested(true)
            verify(uiModelObserver).onChanged(BarcodeReaderViewModel.UiModel.StartCamera)
        }
    }

    @Test
    fun `after request camera permission and permission denied, stop camera`() {
        runBlocking {
            vm.model.observeForever(uiModelObserver)
            vm.onCameraPermissionRequested(false)
            verify(uiModelObserver).onChanged(BarcodeReaderViewModel.UiModel.StopCamera)
        }
    }

    @Test
    fun `while observing Model LiveData and barcode list detected by camera, product list is shown`() {
        runBlocking {
            val mockedItemProductList = mockedRemoteProductList.map { it.toItemProduct() }
            whenever(
                searchProductsByBarcode(
                    any(),
                    any()
                )
            ).thenReturn(mockedRemoteProductList.toSet())
            vm.model.observeForever(uiModelObserver)
            vm.onBarcodeDetected(mockedBarcodeList)
            verify(uiModelObserver).onChanged(
                BarcodeReaderViewModel.UiModel.Content(
                    mockedItemProductList
                )
            )
        }
    }

    @Test
    fun `while observing Navigation LiveData and barcode list detected by camera, product list is shown`() {
        runBlocking {
            val mockedItemProduct = mockedProduct.toItemProduct()
            vm.navigation.observeForever(uiNavigationObserver)
            vm.onProductClicked(mockedItemProduct)
            verify(uiNavigationObserver).onChanged(Event(mockedItemProduct))
        }
    }
}