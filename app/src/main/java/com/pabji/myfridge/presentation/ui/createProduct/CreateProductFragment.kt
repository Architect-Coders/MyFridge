package com.pabji.myfridge.presentation.ui.createProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.common.extensions.setOnTextChange
import com.pabji.myfridge.common.extensions.showKeyboard
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import kotlinx.android.synthetic.main.fragment_create_product.*

class CreateProductFragment : BaseFragment() {

    private lateinit var viewModel: CreateProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { CreateProductViewModel(ProductDBDatasource(app)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFabView()
        setProductNameView()
        viewModel.viewState.observe(this, Observer { updateUI(it) })
    }

    private fun setProductNameView() {
        et_product_name.run {
            setOnTextChange { viewModel.onProductNameChanged(it) }
            activity?.showKeyboard(this)
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
