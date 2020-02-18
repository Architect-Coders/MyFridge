package com.pabji.myfridge.ui.barcode

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.ui.common.BaseViewModel

class BarcodeReaderViewModel : BaseViewModel() {

    private val _model = MutableLiveData<BarcodeReaderViewState>()
    val model: LiveData<BarcodeReaderViewState>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    fun onBarcodeDetected(barcodeList: List<String>) {
        Log.d("BarcodeReaderViewModel", "$barcodeList")
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