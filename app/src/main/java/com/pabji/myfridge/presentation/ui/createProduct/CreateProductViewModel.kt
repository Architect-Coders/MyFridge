package com.pabji.myfridge.presentation.ui.createProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.dtos.ProductDTO
import com.pabji.myfridge.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateProductViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    private val _productValidated = MutableLiveData<Boolean>()
    val productValidated: LiveData<Boolean> = _productValidated
    var name = ""

    fun onProductNameChanged(productName: String) {
        name = productName
        launch {
            _productValidated.value = checkProductValid(productName)
        }
    }

    fun onFabClick() {
        launch {
            productRepository.insert(ProductDTO(name))
        }
    }

    private suspend fun checkProductValid(productName: String) =
        withContext(Dispatchers.Default) { productName.isNotEmpty() }
}