package com.pabji.myfridge.presentation.features.searchProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.data.datasources.SearchDatasource
import com.pabji.myfridge.presentation.extensions.toProductList
import com.pabji.myfridge.presentation.models.Product
import kotlinx.coroutines.launch

class SearchProductsViewModel(private val productRepository: SearchDatasource) : BaseViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    init {
        getProductList()
    }

    private fun getProductList() {
        launch {
            _productList.value = productRepository.getRandomProducts().toProductList()
        }
    }

    fun onProductClicked() {
    }

}