package com.pabji.myfridge.presentation.fragments.createProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import arrow.core.None
import arrow.core.Option
import arrow.core.toOption
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateProductViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _productValidated = MutableLiveData<Boolean>()
    val productValidated: LiveData<Boolean> = _productValidated

    private val _viewState = MutableLiveData<CreateProductViewState>()
    val viewState: LiveData<CreateProductViewState> = _viewState

    var name: Option<String> = None

    fun onProductNameChanged(productName: String) {
        launch {
            name = productName.toOption()
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