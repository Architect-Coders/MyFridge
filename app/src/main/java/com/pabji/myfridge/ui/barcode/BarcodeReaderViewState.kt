package com.pabji.myfridge.ui.barcode

import com.pabji.myfridge.model.ItemProduct

sealed class BarcodeReaderViewState
class Content(val productList: List<ItemProduct>) : BarcodeReaderViewState()

object RequestCameraPermission : BarcodeReaderViewState()
object CameraPermissionGranted : BarcodeReaderViewState()
object CameraPermissionDenied : BarcodeReaderViewState()

class GoToProductDetail(val product: ItemProduct) : BarcodeReaderViewState()
