package com.pabji.myfridge.ui.newProduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.pabji.myfridge.R
import com.pabji.myfridge.ui.common.extensions.setOnTextChange
import com.pabji.myfridge.ui.common.extensions.showKeyboard
import kotlinx.android.synthetic.main.activity_new_product.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class NewProductActivity : AppCompatActivity() {

    private val viewModel: NewProductViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)
        setTitle(R.string.create_product_title)
        viewModel.viewState.observe(this, Observer(::updateUI))
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
