package com.pabji.myfridge.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.ui.common.BaseFragment
import com.pabji.myfridge.ui.common.adapters.ProductListAdapter
import com.pabji.myfridge.ui.common.extensions.gone
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.common.extensions.visible
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity
import com.pabji.myfridge.ui.productList.ProductListViewModel.UiModel
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment() {

    private val viewModel: ProductListViewModel by currentScope.viewModel(this)

    private lateinit var listAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProductListView()
        with(viewModel) {
            model.observe(viewLifecycleOwner, Observer(::updateProductList))
            navigation.observe(viewLifecycleOwner, Observer { event ->
                event.getContent()?.let {
                    startActivity<ProductDetailActivity> {
                        putExtra(ProductDetailActivity.INTENT_PRODUCT, it)
                    }
                }
            })
        }
    }

    private fun setProductListView() {
        rv_product_list.let {
            listAdapter = ProductListAdapter(
                ProductListAdapter.RecyclerType.GRID
            ) { product ->
                viewModel.onProductClicked(product)
            }
            it.adapter = listAdapter
            it.layoutManager = GridLayoutManager(context, GRID_SPAN_COUNT)
        }
    }

    private fun updateProductList(viewState: UiModel) {
        tv_emptyList.gone()
        when (viewState) {
            is UiModel.Content -> listAdapter.productList = viewState.list
            UiModel.EmptyList -> tv_emptyList.visible()
        }
    }

    companion object {
        const val GRID_SPAN_COUNT = 3
        fun newInstance() = ProductListFragment()
    }
}
