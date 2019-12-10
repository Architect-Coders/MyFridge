package com.pabji.myfridge.presentation.features.searchProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.data.datasources.SearchDatasource
import com.pabji.myfridge.presentation.features.productList.ProductListAdapter
import com.pabji.myfridge.presentation.models.Product
import kotlinx.android.synthetic.main.fragment_product_list.*

class SearchProductsFragment : BaseFragment() {

    private lateinit var adapter: ProductListAdapter
    private lateinit var viewModel: SearchProductsViewModel

    companion object {
        fun newInstance() = SearchProductsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { SearchProductsViewModel(SearchDatasource()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProductListView()
        viewModel.productList.observe(this, Observer(::updateProductList))
    }

    private fun setProductListView() {
        rv_product_list.let {
            adapter = ProductListAdapter { product -> viewModel.onProductClicked() }
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateProductList(productList: List<Product>?) {
        productList?.run {
            adapter.productList = this
        }
    }
}
