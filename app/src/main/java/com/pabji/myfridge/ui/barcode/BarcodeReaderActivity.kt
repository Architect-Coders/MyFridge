package com.pabji.myfridge.ui.barcode

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.lifecycle.Observer
import com.pabji.myfridge.R
import com.pabji.myfridge.ui.common.PermissionRequester
import kotlinx.android.synthetic.main.activity_live_preview.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class BarcodeReaderActivity : AppCompatActivity(), OnRequestPermissionsResultCallback {

    private val cameraPermissionRequester = PermissionRequester(this, Manifest.permission.CAMERA)

    private val viewModel: BarcodeReaderViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_live_preview)
        barcode_reader.setBarcodeListener(viewModel::onBarcodeDetected)
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(viewState: BarcodeReaderViewState?) {

        when (viewState) {
            RequestCameraPermission -> cameraPermissionRequester.request(viewModel::onCameraPermissionRequested)
            StartCamera -> barcode_reader.start()
            StopCamera -> finish()
        }
    }

    override fun onPause() {
        super.onPause()
        barcode_reader.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        barcode_reader.destroy()
    }
}
