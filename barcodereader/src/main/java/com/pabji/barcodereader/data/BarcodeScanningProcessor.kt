package com.pabji.barcodereader.data

import android.graphics.Bitmap
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.pabji.barcodereader.view.BarcodeGraphic
import com.pabji.barcodereader.view.CameraImageGraphic
import com.pabji.barcodereader.view.GraphicOverlay
import java.io.IOException

internal class BarcodeScanningProcessor(val listener: (List<FirebaseVisionBarcode>) -> Unit) :
    VisionProcessorBase<List<FirebaseVisionBarcode>>() {

    private val detector: FirebaseVisionBarcodeDetector by lazy {
        FirebaseVision.getInstance().visionBarcodeDetector
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Detector: $e")
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionBarcode>> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(
        originalCameraImage: Bitmap?,
        results: List<FirebaseVisionBarcode>,
        frameMetadata: FrameMetadata,
        graphicOverlay: GraphicOverlay
    ) {
        listener(results)
        graphicOverlay.clear()

        originalCameraImage?.let {
            val imageGraphic =
                CameraImageGraphic(
                    graphicOverlay,
                    it
                )
            graphicOverlay.add(imageGraphic)
        }

        results.forEach {
            val barcodeGraphic =
                BarcodeGraphic(graphicOverlay, it)
            graphicOverlay.add(barcodeGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Barcode detection failed $e")
    }

    companion object {

        private const val TAG = "BarcodeScanProc"
    }
}