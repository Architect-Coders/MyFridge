package com.pabji.myfridge.presentation.ui.createProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.data.repository.ProductRepository
import com.pabji.myfridge.domain.dtos.ProductDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateProductViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _productValidated = MutableLiveData<Boolean>()
    val productValidated: LiveData<Boolean> = _productValidated

    private val _viewState = MutableLiveData<CreateProductViewState>()
    val viewState: LiveData<CreateProductViewState> = _viewState

    var name: String? = null

    fun onProductNameChanged(productName: String) {
        launch {
            name = productName
            _productValidated.value = checkProductValid(productName)
        }
    }

    fun onFabClick() {
        launch {
            productRepository.insert(ProductDTO(name = name))
            _viewState.value = Finish
        }
    }

    private suspend fun checkProductValid(productName: String) =
        withContext(Dispatchers.Default) { productName.isNotEmpty() }
}