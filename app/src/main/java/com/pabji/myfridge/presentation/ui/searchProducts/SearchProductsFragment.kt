package com.pabji.myfridge.presentation.ui.searchProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import com.pabji.myfridge.data.datasources.SearchDatasource
import com.pabji.myfridge.domain.errors.DomainError
import com.pabji.myfridge.domain.errors.SearchError
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.ui.productList.ProductListAdapter
import kotlinx.android.synthetic.main.fragment_product_list.*

class SearchProductsFragment : BaseFragment() {

    private lateinit var adapter: ProductListAdapter
    private lateinit var viewModel: SearchProductsViewModel

    companion object {
        fun newInstance() = SearchProductsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            getViewModel { SearchProductsViewModel(SearchDatasource(), ProductDBDatasource(app)) }
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
        viewModel.let {
            it.productList.observe(this, Observer(::updateProductList))
            it.errorState.observe(this, Observer(::onErrorState))
        }
    }

    private fun setProductListView() {
        rv_product_list.let {
            it.adapter =
                ProductListAdapter { product -> viewModel.onProductClicked(product) }.apply {
                    adapter = this
                }
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateProductList(productList: List<Product>) {
        adapter.productList = productList
    }

    private fun onErrorState(domainError: DomainError?) {
        when (domainError) {
            is SearchError -> showSearchError()
        }
    }

    private fun showSearchError() {
        Toast.makeText(context, R.string.search_error_message, Toast.LENGTH_SHORT).show()
    }
}
