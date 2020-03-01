package com.pabji.barcodereader.utils

import android.graphics.*
import android.util.Log
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.pabji.barcodereader.data.FrameMetadata
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

/** Utils functions for bitmap conversions.  */
internal fun getBitmap(data: ByteBuffer, metadata: FrameMetadata): Bitmap? {
    data.rewind()
    val imageInBuffer = ByteArray(data.limit())
    data[imageInBuffer, 0, imageInBuffer.size]
    try {
        val image = YuvImage(
            imageInBuffer,
            ImageFormat.NV21,
            metadata.width,
            metadata.height,
            null
        )
        val stream = ByteArrayOutputStream()
        image.compressToJpeg(
            Rect(0, 0, metadata.width, metadata.height),
            80,
            stream
        )
        val bmp =
            BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size())
        stream.close()
        return rotateBitmap(
            bmp,
            metadata.rotation
        )
    } catch (e: Exception) {
        Log.e("VisionProcessorBase", "Error: " + e.message)
    }
    return null
}

// Rotates a bitmap if it is converted from a bytebuffer.
private fun rotateBitmap(bitmap: Bitmap, rotation: Int): Bitmap = Bitmap.createBitmap(
    bitmap,
    0,
    0,
    bitmap.width,
    bitmap.height,
    Matrix().apply { postRotate(getRotationDegree(rotation)) },
    true
)

private fun getRotationDegree(rotation: Int): Float = when (rotation) {
    FirebaseVisionImageMetadata.ROTATION_90 -> 90f
    FirebaseVisionImageMetadata.ROTATION_180 -> 180f
    FirebaseVisionImageMetadata.ROTATION_270 -> 270f
    else -> 0f
}