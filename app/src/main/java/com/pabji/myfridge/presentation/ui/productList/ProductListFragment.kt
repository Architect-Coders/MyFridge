package com.pabji.myfridge.presentation.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.common.extensions.startActivity
import com.pabji.myfridge.data.datasources.ProductDBDatasourceImpl
import com.pabji.myfridge.data.datasources.ProductNetworkDatasourceImpl
import com.pabji.myfridge.data.repository.ProductRepositoryImpl
import com.pabji.myfridge.presentation.adapters.ProductListAdapter
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.ui.productDetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : BaseFragment() {

    private lateinit var adapter: ProductListAdapter
    private lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel {
            ProductListViewModel(
                ProductRepositoryImpl(
                    ProductDBDatasourceImpl(app),
                    ProductNetworkDatasourceImpl()
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProductListView()
        viewModel.productList.observe(this, Observer(::updateProductList))
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

    private fun updateProductList(productList: List<Product>) {
        adapter.productList = productList
    }

    companion object {
        fun newInstance() = ProductListFragment()
    }
}
