package com.pabji.myfridge.presentation.fragments.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import com.pabji.myfridge.presentation.adapters.ProductListAdapter
import com.pabji.myfridge.presentation.fragments.main.MainFragmentDirections
import com.pabji.myfridge.presentation.models.Product
import kotlinx.android.synthetic.main.fragment_product_list.*

class ProductListFragment : BaseFragment() {

    private lateinit var adapter: ProductListAdapter
    private lateinit var viewModel: ProductListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel { ProductListViewModel(ProductDBDatasource(app)) }
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
            adapter = ProductListAdapter { product ->
                MainFragmentDirections.actionMainFragmentToProductDetailFragment(product).run {
                    navController.navigate(this)
                }
            }
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateProductList(productList: List<Product>?) {
        productList?.run {
            adapter.productList = this
        }
    }

    companion object {
        fun newInstance() = ProductListFragment()
    }
}
