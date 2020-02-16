package com.pabji.myfridge.ui.barcode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.content.ContextCompat
import com.pabji.myfridge.R
import kotlinx.android.synthetic.main.activity_live_preview.*
import java.io.IOException

class BarcodeReaderActivity : AppCompatActivity(), OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_live_preview)
        barcode_reader.setBarcodeListener {
            Log.d(TAG, "$it")
        }
    }

    private fun startCameraSource() {
        try {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                barcode_reader.start()
            } else {
                getRuntimePermissions()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Unable to start camera source.", e)
            barcode_reader.stop()
        }
    }

    public override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        startCameraSource()
    }

    override fun onPause() {
        super.onPause()
        barcode_reader.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        barcode_reader.destroy()
    }

    private fun getRuntimePermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUESTS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "Permission granted!")
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            barcode_reader.start()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val TAG = "LivePreviewActivity"
        private const val PERMISSION_REQUESTS = 1
    }
}
