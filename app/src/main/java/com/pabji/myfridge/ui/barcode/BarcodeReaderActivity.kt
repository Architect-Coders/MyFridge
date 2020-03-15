package com.pabji.myfridge.ui.barcode

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.ui.barcode.BarcodeReaderViewModel.UiModel
import com.pabji.myfridge.ui.common.PermissionRequester
import com.pabji.myfridge.ui.common.adapters.ProductListAdapter
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.common.extensions.visible
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_live_preview.*
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.scope.viewModel

class BarcodeReaderActivity : AppCompatActivity(), OnRequestPermissionsResultCallback {

    private val cameraPermissionRequester = PermissionRequester(this, Manifest.permission.CAMERA)
    private lateinit var adapter: ProductListAdapter

    private val viewModel: BarcodeReaderViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_live_preview)
        barcode_reader.setBarcodeListener(viewModel::onBarcodeDetected)
        initRecycler()
        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.navigation.observe(this, Observer { event ->
            event.getContent()?.let {
                startActivity<ProductDetailActivity> {
                    putExtra(ProductDetailActivity.INTENT_PRODUCT, it)
                }
            }
        })
    }

    private fun barcodeStart() {
        try {
            barcode_reader.start()
        } catch (e: Exception) {
            finish()
        }
    }

    private fun initRecycler() {
        rv_product_list.let {
            it.adapter =
                ProductListAdapter { product ->
                    viewModel.onProductClicked(product)
                }.apply {
                    adapter = this
                }
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        }
    }

    private fun updateUi(viewState: UiModel?) {
        when (viewState) {
            UiModel.RequestCameraPermission -> cameraPermissionRequester.request(viewModel::onCameraPermissionRequested)
            is UiModel.Content -> setProductList(viewState.productList)
            UiModel.StartCamera -> barcodeStart()
            UiModel.StopCamera -> finish()
        }
    }

    private fun setProductList(list: List<ItemProduct>) {
        rv_product_list.visible()
        adapter.productList = list
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    override fun onPause() {
        super.onPause()
        barcode_reader.stop()
    }

    override fun onDestroy() {
        barcode_reader.destroy()
        super.onDestroy()
    }
}
