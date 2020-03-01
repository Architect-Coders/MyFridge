package com.pabji.barcodereader.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

/** Draw camera image to background.  */
internal class CameraImageGraphic(overlay: GraphicOverlay?, private val bitmap: Bitmap) :
    GraphicOverlay.Graphic(overlay!!) {
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(
            bitmap,
            null,
            Rect(0, 0, canvas.width, canvas.height),
            null
        )
    }

}