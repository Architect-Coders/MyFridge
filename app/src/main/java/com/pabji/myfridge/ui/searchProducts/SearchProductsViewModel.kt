package com.pabji.myfridge.ui.searchProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.SearchError
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.uiModels.toItemProduct
import com.pabji.usecases.SearchProductsByTerm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchProductsViewModel(private val searchProductsByTerm: SearchProductsByTerm) :
    BaseViewModel() {

    private val _viewState = MutableLiveData<SearchProductsViewState>()
    val viewState: LiveData<SearchProductsViewState> = _viewState

    init {
        getProductList()
    }

    private fun getProductList() {
        launch {
            _viewState.value = Loading
            searchProducts()
        }
    }

    fun onSearch(searchTerm: String) {
        launch {
            _viewState.value = Loading
            searchProducts(searchTerm)
        }
    }

    private suspend fun searchProducts(searchTerm: String = "") {
        val result = withContext(Dispatchers.IO) { searchProductsByTerm(searchTerm) }

        if (result.isEmpty()) {
            _viewState.value = ShowError(SearchError)
        } else {
            _viewState.value = ShowProductList(result.map { it.toItemProduct() })
        }
    }
}