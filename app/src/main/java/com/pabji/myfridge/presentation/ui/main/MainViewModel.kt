package com.pabji.myfridge.presentation.ui.main

import com.pabji.myfridge.common.BaseViewModel

class MainViewModel : BaseViewModel() {

    var navigator: MainNavigator? = null

    init {
        navigator?.goToProductList()
    }

    fun onFabClicked() {
        navigator?.goToCreateProduct(true)
    }

}