package com.pabji.myfridge.ui.searchProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.fold
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.uiModels.toItemProduct
import com.pabji.usecases.SearchProductByTerm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchProductsViewModel(private val searchProductByTerm: SearchProductByTerm) :
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
        withContext(Dispatchers.IO) {
            searchProductByTerm(searchTerm).fold({
                _viewState.postValue(ShowError(it))
            }) { list ->
                _viewState.postValue(ShowProductList(list.map { it.toItemProduct() }))
            }
        }
    }
}