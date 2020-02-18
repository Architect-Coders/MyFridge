package com.pabji.myfridge.ui.barcode

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProductList
import com.pabji.myfridge.ui.common.PermissionRequester
import com.pabji.myfridge.ui.common.adapters.ProductListAdapter
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.common.extensions.visible
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_live_preview.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class BarcodeReaderActivity : AppCompatActivity(), OnRequestPermissionsResultCallback {

    private val cameraPermissionRequester = PermissionRequester(this, Manifest.permission.CAMERA)
    private lateinit var adapter: ProductListAdapter

    private val viewModel: BarcodeReaderViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_live_preview)
        barcode_reader.setBarcodeListener(viewModel::onBarcodeDetected)
        initRecycler()
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun initRecycler() {
        rv_product_list.let {
            it.adapter =
                ProductListAdapter { product ->
                    startActivity<ProductDetailActivity> {
                        putExtra(ProductDetailActivity.INTENT_PRODUCT, product)
                    }
                }.apply {
                    adapter = this
                }
            it.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true)
        }
    }

    private fun updateUi(viewState: BarcodeReaderViewState?) {

        when (viewState) {
            RequestCameraPermission -> cameraPermissionRequester.request(viewModel::onCameraPermissionRequested)
            is ProductList -> setProductList(viewState.productList)
            StartCamera -> barcode_reader.start()
            StopCamera -> finish()
        }
    }

    private fun setProductList(list: List<ItemProductList>) {
        rv_product_list.visible()
        adapter.productList = list
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
