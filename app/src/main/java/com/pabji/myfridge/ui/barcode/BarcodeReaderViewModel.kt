package com.pabji.myfridge.ui.barcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.Product
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.usecases.SearchProductsByBarcode
import kotlinx.coroutines.launch

class BarcodeReaderViewModel(private val searchProductsByBarcode: SearchProductsByBarcode) :
    BaseViewModel() {

    private val _model = MutableLiveData<BarcodeReaderViewState>()
    val model: LiveData<BarcodeReaderViewState> = _model

    private val _permissionModel = MutableLiveData<BarcodeReaderViewState>()
    val permissionModel: LiveData<BarcodeReaderViewState> = _permissionModel

    private val _navigationModel = MutableLiveData<BarcodeReaderViewState>()
    val navigationModel: LiveData<BarcodeReaderViewState> = _navigationModel


    private var barcodeListDetected = setOf<Product>()

    fun onBarcodeDetected(barcodeList: List<String>) {
        launch {
            barcodeListDetected = searchProductsByBarcode(barcodeListDetected, barcodeList.toSet())
            _model.value = Content(barcodeListDetected.map { it.toItemProduct() })
        }
    }

    fun onCameraPermissionRequested(permissionGranted: Boolean) {
        _permissionModel.value = when (permissionGranted) {
            true -> CameraPermissionGranted
            false -> CameraPermissionDenied
        }
    }

    fun checkPermissions() {
        _permissionModel.value = RequestCameraPermission
    }

    fun onProductClicked(product: ItemProduct) {
        _navigationModel.value = GoToProductDetail(product)
    }
}