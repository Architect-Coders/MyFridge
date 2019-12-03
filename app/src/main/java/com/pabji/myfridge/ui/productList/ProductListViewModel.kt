package com.pabji.myfridge.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.ui.extensions.toProductDTOList
import com.pabji.myfridge.ui.extensions.toProductList
import kotlinx.coroutines.launch

class ProductListViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> get() = _viewState

    init {
        launch {
            _viewState.value = Loading
            val products = productRepository.getAllProducts().toProductList()
            _viewState.value = MyProductList(products)
        }
    }

    fun onFabClicked() {
        launch {
            val productList = (1..10).map { Product("Product $it") }
            productRepository.insertAll(productList.toProductDTOList())
        }
    }


}