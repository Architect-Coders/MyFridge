package com.pabji.myfridge.ui.barcode

sealed class BarcodeReaderViewState
object Loading : BarcodeReaderViewState()
object RequestCameraPermission : BarcodeReaderViewState()
object StartCamera : BarcodeReaderViewState()
object StopCamera : BarcodeReaderViewState()
