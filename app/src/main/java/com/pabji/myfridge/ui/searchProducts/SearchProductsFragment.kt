package com.pabji.myfridge.ui.searchProducts

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.ui.common.BaseFragment
import com.pabji.myfridge.ui.common.adapters.ProductListAdapter
import com.pabji.myfridge.ui.common.extensions.gone
import com.pabji.myfridge.ui.common.extensions.onTextChange
import com.pabji.myfridge.ui.common.extensions.setVisible
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.common.extensions.visible
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity
import com.pabji.myfridge.ui.searchProducts.SearchProductsViewModel.UiModel
import kotlinx.android.synthetic.main.activity_main.bottom_navigation
import kotlinx.android.synthetic.main.fragment_search_products.progress_bar
import kotlinx.android.synthetic.main.fragment_search_products.rv_product_list
import kotlinx.android.synthetic.main.fragment_search_products.tv_emptyList
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class SearchProductsFragment : BaseFragment() {

    private var instanceState: Bundle? = null
    private lateinit var searchView: SearchView
    private lateinit var listAdapter: ProductListAdapter

    private val viewModel: SearchProductsViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onSearch()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProductListView()
        with(viewModel) {
            model.observe(viewLifecycleOwner, Observer(::updateUI))
            navigation.observe(viewLifecycleOwner, Observer { event ->
                event.getContent()?.let {
                    startActivity<ProductDetailActivity> {
                        putExtra(ProductDetailActivity.INTENT_PRODUCT, it)
                    }
                }
            })
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        instanceState = savedInstanceState
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_toolbar_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        val searchMenuItem = menu.findItem(R.id.action_toolbar_search) as MenuItem
        searchView = (searchMenuItem.actionView as SearchView).apply {
            setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))
            setOnQueryTextFocusChangeListener { _, isVisible ->
                bottom_navigation.setVisible(!isVisible)
            }
            instanceState?.getCharSequence(SEARCH_TERM)?.let {
                searchMenuItem.expandActionView()
                setQuery(it, false)
            }
            instanceState?.remove(SEARCH_TERM)
            onTextChange { text -> viewModel.onSearch(text) }
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
                    viewModel.onProductClicked(product)
                }.apply {
                    listAdapter = this
                }
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateUI(viewState: UiModel?) {
        tv_emptyList.gone()
        progress_bar.hide()

        when (viewState) {
            UiModel.Loading -> progress_bar.show()
            is UiModel.Content -> {
                rv_product_list.visible()
                listAdapter.productList = viewState.list
            }
            UiModel.EmptyList -> {
                tv_emptyList.visible()
                rv_product_list.gone()
            }
        }
    }

    companion object {
        fun newInstance() = SearchProductsFragment()
        const val SEARCH_TERM = "search_term"
    }
}
