package com.pabji.myfridge.ui.barcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.model.ItemProductList
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.usecases.SearchProductsByBarcode
import kotlinx.coroutines.launch

class BarcodeReaderViewModel(private val searchProductsByBarcode: SearchProductsByBarcode) :
    BaseViewModel() {

    private val _model = MutableLiveData<BarcodeReaderViewState>()
    val model: LiveData<BarcodeReaderViewState>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val barcodeListDetected = mutableListOf<String>()
    private val mutableProductList = mutableListOf<ItemProductList>()

    fun onBarcodeDetected(barcodeList: List<String>) {
        if (barcodeListDetected.isEmpty()) {
            if (barcodeList.isNotEmpty()) {
                launch {
                    mutableProductList.addAll(searchProductsByBarcode(barcodeList).map { it.toItemProduct() })
                    _model.value = ProductList(mutableProductList)
                    barcodeListDetected.addAll(barcodeList)
                }
            }
        } else {
            val result: List<String> = barcodeListDetected.subtract(barcodeList).toList()
            if (result.isNotEmpty()) {
                launch {
                    mutableProductList.addAll(searchProductsByBarcode(barcodeList).map { it.toItemProduct() })
                    _model.value = ProductList(mutableProductList)
                    barcodeListDetected.addAll(result)
                }
            }
        }
    }

    private fun refresh() {
        _model.value = RequestCameraPermission
    }

    fun onCameraPermissionRequested(permissionGranted: Boolean) {
        _model.value = when (permissionGranted) {
            true -> StartCamera
            false -> StopCamera
        }
    }
}