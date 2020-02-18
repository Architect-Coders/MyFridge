package com.pabji.myfridge.ui.barcode

import com.pabji.myfridge.model.ItemProductList

sealed class BarcodeReaderViewState
object Loading : BarcodeReaderViewState()
object RequestCameraPermission : BarcodeReaderViewState()
class ProductList(val productList: List<ItemProductList>) : BarcodeReaderViewState()
object StartCamera : BarcodeReaderViewState()
object StopCamera : BarcodeReaderViewState()
