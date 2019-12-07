package com.pabji.myfridge.presentation.ui.createProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.getViewModel
import com.pabji.myfridge.data.datasources.ProductDBDatasource

class CreateProductFragment : BaseFragment() {

    private lateinit var viewModel: CreateProductViewModel

    companion object {
        fun newInstance() = CreateProductFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.run {
            viewModel = getViewModel { CreateProductViewModel(ProductDBDatasource(application)) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_product, container, false)
    }
}
