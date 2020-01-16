package com.pabji.myfridge.presentation.ui.searchProducts

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.internal.view.SupportMenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.common.extensions.onTextChange
import com.pabji.myfridge.common.extensions.setVisible
import com.pabji.myfridge.common.extensions.startActivity
import com.pabji.myfridge.data.datasources.ProductDBDatasourceImpl
import com.pabji.myfridge.data.datasources.ProductNetworkDatasourceImpl
import com.pabji.myfridge.data.repository.ProductRepositoryImpl
import com.pabji.myfridge.domain.errors.DomainError
import com.pabji.myfridge.domain.errors.SearchError
import com.pabji.myfridge.presentation.adapters.ProductListAdapter
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.ui.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_products.*

class SearchProductsFragment : BaseFragment() {

    private var instanceState: Bundle? = null
    private lateinit var searchView: SearchView
    private lateinit var adapter: ProductListAdapter
    private lateinit var viewModel: SearchProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel {
            SearchProductsViewModel(
                ProductRepositoryImpl(
                    ProductDBDatasourceImpl(app),
                    ProductNetworkDatasourceImpl()
                )
            )
        }
        setHasOptionsMenu(true)
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
        viewModel.viewState.observe(this, Observer(::updateUI))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        instanceState = savedInstanceState
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)

        activity?.run {
            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
            val searchMenuItem = menu.findItem(R.id.action_search) as SupportMenuItem
            searchView = (searchMenuItem.actionView as SearchView).apply {
                setSearchableInfo(searchManager.getSearchableInfo(componentName))
                setOnQueryTextFocusChangeListener { _, isVisible ->
                    bottom_navigation.setVisible(!isVisible)
                }
                instanceState?.run {
                    getCharSequence(SEARCH_TERM)?.let {
                        searchMenuItem.expandActionView()
                        setQuery(it, false)
                    }
                    remove(SEARCH_TERM)
                }
                onTextChange { text -> viewModel.onSearch(text) }
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (::searchView.isInitialized) {
            outState.putCharSequence(SEARCH_TERM, searchView.query)
        }
        super.onSaveInstanceState(outState)
    }

    private fun setProductListView() {
        rv_product_list.let {
            it.adapter =
                ProductListAdapter { product ->
                    startActivity<ProductDetailActivity> {
                        putExtra(ProductDetailActivity.INTENT_PRODUCT, product)
                    }
                }.apply {
                    adapter = this
                }
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateUI(viewState: SearchProductsViewState?) {
        when (viewState) {
            Loading -> progress_bar.show()
            is ShowProductList -> showProductList(viewState.list)
            is ShowError -> showError(viewState.error)
        }
    }

    private fun showProductList(list: List<Product>) {
        progress_bar.hide()
        adapter.productList = list
    }

    private fun showError(error: DomainError) {
        progress_bar.hide()
        when (error) {
            is SearchError -> showSearchError()
        }
    }

    private fun showSearchError() {
        Toast.makeText(context, R.string.search_error_message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = SearchProductsFragment()
        const val SEARCH_TERM = "search_term"
    }
}
