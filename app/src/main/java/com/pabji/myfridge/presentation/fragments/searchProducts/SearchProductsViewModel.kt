package com.pabji.myfridge.presentation.fragments.searchProducts

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

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> = _productList

    init {
        getProductList()
    }

    private fun getProductList() {
        launch {
            searchRepository.getRandomProducts().fold(::onErrorResult) {
                _productList.value = it.toProductList()
            }
        }
    }

    fun onProductClicked(product: Product) {
        launch {
            productRepository.insert(product.toProductDTO())
        }
    }

    fun onSearch(searchTerm: String) {
        launch {
            searchRepository.searchProductsByName(searchTerm).fold(::onErrorResult) {
                _productList.value = it.toProductList()
            }
        }
    }

}