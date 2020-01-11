package com.pabji.myfridge.presentation.ui.searchProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.data.repository.ProductRepository
import com.pabji.myfridge.domain.errors.SearchError
import com.pabji.myfridge.presentation.extensions.toProductList
import kotlinx.coroutines.launch

class SearchProductsViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

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
        productRepository.searchProducts(searchTerm).run {
            if (isEmpty()) {
                _viewState.value = ShowError(SearchError)
            } else {
                _viewState.value = ShowProductList(toProductList())
            }
        }
    }
}