package com.pabji.myfridge.presentation.features.main

import com.pabji.myfridge.common.extensions.startActivity
import com.pabji.myfridge.presentation.features.createProduct.CreateProductActivity

class MainNavigator(val activity: MainActivity) {

    fun goToCreateProduct() = activity.startActivity<CreateProductActivity>()
}