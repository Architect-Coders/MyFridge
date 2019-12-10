package com.pabji.myfridge.presentation.features.main

import com.pabji.myfridge.common.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    var navigator: MainNavigator? = null

    fun onFabClick() {
        launch { navigator?.goToCreateProduct() }
    }
}
