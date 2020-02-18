package com.pabji.barcodereader

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.barcode_reader_view.view.*

class BarcodeReaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private val TAG = BarcodeReaderView::class.java.simpleName
    }

    init {
        inflate(context, R.layout.barcode_reader_view, this)
        cameraPreview.create()
    }

    fun start() {
        try {
            Log.e(TAG, "Start camera preview")
            cameraPreview.start(graphicOverlay)
        } catch (e: Exception) {
            Log.e(TAG, "Unable to start camera source.", e)
            cameraPreview.release()
        }
    }

    fun setBarcodeListener(listener: (List<String>) -> Unit) =
        cameraPreview.setBarcodeListener(listener)

    fun stop() = cameraPreview.stop()

    fun destroy() = cameraPreview.release()
}