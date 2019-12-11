package com.pabji.myfridge.presentation.ui.searchProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.domain.repositories.SearchRepository
import com.pabji.myfridge.presentation.extensions.toProductDTO
import com.pabji.myfridge.presentation.extensions.toProductList
import com.pabji.myfridge.presentation.models.Product
import kotlinx.coroutines.launch

class SearchProductsViewModel(
    private val searchRepository: SearchRepository,
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _productList = MutableLiveData<SearchProductsViewState>()
    val productList: LiveData<SearchProductsViewState> = _productList

    init {
        getProductList()
    }

    private fun getProductList() {
        val tempString = "yogur griego"
        launch {
            _productList.value = searchRepository.searchProductsByName(tempString).fold({
                SearchError
            }) {
                SearchResult(it.toProductList())
            }
        }
    }

    fun onProductClicked(product: Product) {
        launch {
            productRepository.insert(product.toProductDTO())
        }
    }

}