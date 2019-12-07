package com.pabji.myfridge.ui.productList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import com.pabji.myfridge.ui.extensions.getViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class ProductListActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductListViewModel
    private val adapter = ProductListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = getViewModel { ProductListViewModel(ProductDBDatasource(application)) }
        viewModel.productList.observe(this, Observer(::updateProductList))


        rv_product_list.let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        fab.setOnClickListener { viewModel.onFabClicked() }
    }

    private fun updateProductList(productList: List<Product>?) {
        productList?.run {
            adapter.productList = this
        }
    }
}
