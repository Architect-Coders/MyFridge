package com.pabji.myfridge.presentation.ui.main

import com.pabji.myfridge.R
import com.pabji.myfridge.common.setFragment
import com.pabji.myfridge.presentation.ui.createProduct.CreateProductFragment
import com.pabji.myfridge.presentation.ui.productList.ProductListFragment

class MainNavigator(private val activity: MainActivity) {

    companion object {
        const val containerId = R.id.fragment_container
    }

    init {
        goToProductList()
    }

    fun goToProductList(addToBackStack: Boolean = false) {
        activity.setFragment(ProductListFragment.newInstance(), containerId, addToBackStack)
    }

    fun goToCreateProduct(addToBackStack: Boolean = false) {
        activity.setFragment(CreateProductFragment.newInstance(), containerId, addToBackStack)
    }
}