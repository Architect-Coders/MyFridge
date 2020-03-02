package com.pabji.myfridge.ui.barcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.Product
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.Event
import com.pabji.usecases.SearchProductsByBarcode
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class BarcodeReaderViewModel(
    private val searchProductsByBarcode: SearchProductsByBarcode,
    uiDispatcher: CoroutineDispatcher
) :
    BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private val _navigation = MutableLiveData<Event<ItemProduct>>()
    val navigation: LiveData<Event<ItemProduct>> = _navigation

    private var barcodeListDetected = setOf<Product>()

    sealed class UiModel {
        data class Content(val productList: List<ItemProduct>) : UiModel()
        object RequestCameraPermission : UiModel()
        object StartCamera : UiModel()
        object StopCamera : UiModel()
    }

    fun refresh() {
        _model.value = UiModel.RequestCameraPermission
    }

    fun onBarcodeDetected(barcodeList: List<String>) {
        launch {
            barcodeListDetected = searchProductsByBarcode(barcodeListDetected, barcodeList.toSet())
            _model.value = UiModel.Content(barcodeListDetected.map { it.toItemProduct() })
        }
    }

    fun onCameraPermissionRequested(permissionGranted: Boolean) {
        _model.value = when (permissionGranted) {
            true -> UiModel.StartCamera
            false -> UiModel.StopCamera
        }
    }

    fun onProductClicked(product: ItemProduct) {
        _navigation.value = Event(product)
    }
}