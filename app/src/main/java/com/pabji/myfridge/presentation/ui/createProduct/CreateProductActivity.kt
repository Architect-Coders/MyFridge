package com.pabji.myfridge.presentation.ui.createProduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pabji.myfridge.MyApp
import com.pabji.myfridge.R
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.common.extensions.setOnTextChange
import com.pabji.myfridge.common.extensions.showKeyboard
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import kotlinx.android.synthetic.main.activity_create_product.*

class CreateProductActivity : AppCompatActivity() {

    private lateinit var viewModel: CreateProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)
        setSupportActionBar(toolbar)

        viewModel = getViewModel { CreateProductViewModel(ProductDBDatasource(MyApp.app)) }.also {
            it.viewState.observe(this, Observer(::updateUI))
        }
        setFabView()
        setProductNameView()
    }

    private fun setProductNameView() {
        et_product_name.run {
            setOnTextChange { viewModel.onProductNameChanged(it) }
            showKeyboard(this)
        }
    }

    private fun setFabView() {
        fab.let {
            it.isEnabled = false
            it.setOnClickListener { viewModel.onFabClick() }
            viewModel.productValidated.observe(this, Observer { isValid ->
                it.isEnabled = isValid ?: false
            })
        }
    }

    private fun updateUI(viewState: CreateProductViewState?) {
        when (viewState) {
            Finish -> finish()
        }
    }
}
