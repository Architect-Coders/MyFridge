package com.pabji.barcodereader.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import com.pabji.barcodereader.R
import com.pabji.barcodereader.data.NoExistCameraException
import kotlinx.android.synthetic.main.barcode_reader_view.view.*
import java.io.IOException

class BarcodeReaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private val TAG = BarcodeReaderView::class.java.simpleName
    }

    init {
        inflate(
            context,
            R.layout.barcode_reader_view, this
        )
        cameraPreview.create()
    }

    @Throws(IOException::class, SecurityException::class, NoExistCameraException::class)
    fun start() {
        Log.e(TAG, "Start camera preview")
        cameraPreview.start(graphicOverlay)
    }

    fun setBarcodeListener(listener: (List<String>) -> Unit) =
        cameraPreview.setBarcodeListener(listener)

    fun stop() = cameraPreview.stop()

    @Throws(IOException::class, SecurityException::class)
    fun destroy() = cameraPreview.release()
}