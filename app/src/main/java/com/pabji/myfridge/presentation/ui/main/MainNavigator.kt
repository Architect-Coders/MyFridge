package com.pabji.myfridge.presentation.ui.main

import com.pabji.myfridge.common.extensions.startActivity
import com.pabji.myfridge.presentation.ui.createProduct.CreateProductActivity

class MainNavigator(val activity: MainActivity) {

    fun goToCreateProduct() = activity.startActivity<CreateProductActivity>()
}