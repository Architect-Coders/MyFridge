package com.pabji.myfridge.presentation.ui.createProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.getViewModel
import com.pabji.myfridge.common.setOnTextChange
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import kotlinx.android.synthetic.main.fragment_create_product.*
import kotlinx.android.synthetic.main.fragment_create_product.view.*

class CreateProductFragment : BaseFragment() {

    private lateinit var viewModel: CreateProductViewModel

    companion object {
        fun newInstance() = CreateProductFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { CreateProductViewModel(ProductDBDatasource(app)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_product, container, false)
        view.et_product_name.setOnTextChange { viewModel.onProductNameChanged(it) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.let {
            it.isEnabled = false
            it.setOnClickListener { viewModel.onFabClick() }
            viewModel.productValidated.observe(this, Observer { isValid ->
                it.isEnabled = isValid ?: false
            })
        }
    }
}
