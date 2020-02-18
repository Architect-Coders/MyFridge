package com.pabji.myfridge.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProductList
import com.pabji.myfridge.ui.common.BaseFragment
import com.pabji.myfridge.ui.common.adapters.ProductListAdapter
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment() {

    private lateinit var adapter: ProductListAdapter
    private val viewModel: ProductListViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProductListView()
        viewModel.productList.observe(viewLifecycleOwner, Observer(::updateProductList))
    }

    private fun setProductListView() {
        rv_product_list.let {
            adapter = ProductListAdapter(ProductListAdapter.RecyclerType.GRID) { product ->
                startActivity<ProductDetailActivity> {
                    putExtra(ProductDetailActivity.INTENT_PRODUCT, product)
                }
            }
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun updateProductList(productList: List<ItemProductList>) {
        adapter.productList = productList
    }

    companion object {
        fun newInstance() = ProductListFragment()
    }
}
