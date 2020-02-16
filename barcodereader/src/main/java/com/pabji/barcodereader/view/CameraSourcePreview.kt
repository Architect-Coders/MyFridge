package com.pabji.barcodereader.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import com.google.firebase.ml.common.FirebaseMLException
import com.pabji.barcodereader.data.BarcodeScanningProcessor
import com.pabji.barcodereader.data.CameraSource
import com.pabji.barcodereader.utils.isPortrait
import kotlinx.android.synthetic.main.barcode_reader_view.view.*
import java.io.IOException
import kotlin.math.max
import kotlin.math.min

internal class CameraSourcePreview(
    mContext: Context,
    attrs: AttributeSet?
) : ViewGroup(mContext, attrs) {

    private var barcodeListener: ((List<String>) -> Unit)? = null
    private val mSurfaceView: SurfaceView = SurfaceView(mContext)
    private var mStartRequested = false
    private var mSurfaceAvailable = false
    private var mCameraSource: CameraSource? = null
    private var mOverlay: GraphicOverlay? = null

    init {
        mSurfaceView.holder.addCallback(SurfaceCallback())
        addView(mSurfaceView)
    }

    fun setBarcodeListener(listener: (List<String>) -> Unit) {
        barcodeListener = listener
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    @Throws(IOException::class, SecurityException::class)
    fun start(overlay: GraphicOverlay?) {
        mOverlay = overlay
        mCameraSource?.run {
            mStartRequested = true
            startIfReady()
        } ?: stop()
    }

    fun stop() = mCameraSource?.stop()

    fun release() {
        mCameraSource?.release()
        mCameraSource = null
    }

    fun create() {
        if (mCameraSource == null) {
            mCameraSource = CameraSource(
                context,
                graphicOverlay
            )
        }

        try {
            mCameraSource?.setMachineLearningFrameProcessor(BarcodeScanningProcessor { barcodeList ->
                barcodeListener?.invoke(barcodeList.mapNotNull { it.rawValue })
            })
        } catch (e: FirebaseMLException) {
            Log.e(TAG, "can not create camera source")
        }
    }

    @SuppressLint("MissingPermission")
    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {

        val (previewWidth, previewHeight) = getPreviewSize()

        val viewWidth = right - left
        val viewHeight = bottom - top

        val widthRatio = viewWidth.toFloat() / previewWidth.toFloat()
        val heightRatio = viewHeight.toFloat() / previewHeight.toFloat()

        var childXOffset = 0
        var childYOffset = 0

        val childWidth: Int
        val childHeight: Int

        if (widthRatio > heightRatio) {
            childWidth = viewWidth
            childHeight = (previewHeight.toFloat() * widthRatio).toInt()
            childYOffset = (childHeight - viewHeight) / 2
        } else {
            childWidth = (previewWidth.toFloat() * heightRatio).toInt()
            childHeight = viewHeight
            childXOffset = (childWidth - viewWidth) / 2
        }

        for (i in 0 until childCount) {
            getChildAt(i).layout(
                -1 * childXOffset, -1 * childYOffset,
                childWidth - childXOffset, childHeight - childYOffset
            )
        }

        try {
            startIfReady()
        } catch (e: IOException) {
            Log.e(
                TAG,
                "Could not start camera source.",
                e
            )
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    @Throws(IOException::class, SecurityException::class)
    private fun startIfReady() {
        if (mStartRequested && mSurfaceAvailable) {
            mCameraSource?.start()
            mOverlay?.run {
                mCameraSource?.previewSize?.let {
                    val min = min(it.width, it.height)
                    val max = max(it.width, it.height)
                    if (context.isPortrait()) {
                        setCameraInfo(min, max)
                    } else {
                        setCameraInfo(max, min)
                    }
                }
                clear()
            }
            mStartRequested = false
        }
    }

    private fun getPreviewSize(): Pair<Int, Int> {
        var previewWidth =
            DEFAULT_PREVIEW_WIDTH
        var previewHeight =
            DEFAULT_PREVIEW_HEIGHT
        mCameraSource?.run {
            previewSize?.let { size ->
                previewWidth = size.width
                previewHeight = size.height
            }
        }
        if (context.isPortrait()) {
            val tmp = previewWidth
            previewWidth = previewHeight
            previewHeight = tmp
        }
        return Pair(previewWidth, previewHeight)
    }

    private inner class SurfaceCallback : SurfaceHolder.Callback {
        override fun surfaceCreated(surface: SurfaceHolder) {
            mSurfaceAvailable = true
            try {
                startIfReady()
            } catch (se: SecurityException) {
                Log.e(TAG, "Do not have permission to start the camera", se)
            } catch (e: IOException) {
                Log.e(TAG, "Could not start camera source.", e)
            }
        }

        override fun surfaceDestroyed(surface: SurfaceHolder) {
            mSurfaceAvailable = false
        }

        override fun surfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
        }
    }

    companion object {
        private val TAG = CameraSourcePreview::class.java.simpleName
        private const val DEFAULT_PREVIEW_WIDTH = 320
        private const val DEFAULT_PREVIEW_HEIGHT = 240
    }
}