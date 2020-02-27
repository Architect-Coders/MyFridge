package com.pabji.myfridge.ui.searchProducts

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.internal.view.SupportMenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.domain.DomainError
import com.pabji.domain.SearchError
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.ui.common.BaseFragment
import com.pabji.myfridge.ui.common.adapters.ProductListAdapter
import com.pabji.myfridge.ui.common.extensions.onTextChange
import com.pabji.myfridge.ui.common.extensions.setVisible
import com.pabji.myfridge.utils.goToProductDetail
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_products.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class SearchProductsFragment : BaseFragment() {

    private var instanceState: Bundle? = null
    private lateinit var searchView: SearchView
    private lateinit var adapter: ProductListAdapter

    private val viewModel: SearchProductsViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.viewState.observe(viewLifecycleOwner, Observer(::updateUI))
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
                    goToProductDetail(product)
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

    private fun showProductList(list: List<ItemProduct>) {
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
