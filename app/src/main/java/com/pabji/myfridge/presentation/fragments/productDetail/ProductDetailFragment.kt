package com.pabji.myfridge.presentation.fragments.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import com.pabji.myfridge.presentation.fragments.productList.ProductDetailViewModel

class ProductDetailFragment : BaseFragment() {

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { ProductDetailViewModel(ProductDBDatasource(app)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

}
