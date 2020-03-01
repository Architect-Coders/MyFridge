package com.pabji.myfridge.ui.newProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.Product
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.usecases.SaveProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewProductViewModel(private val saveProduct: SaveProduct, uiDispatcher: CoroutineDispatcher) :
    BaseViewModel(uiDispatcher) {

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
            name?.run {
                saveProduct(Product(name = this))
                _viewState.value = Finish
            }
        }
    }

    private suspend fun checkProductValid(productName: String) =
        withContext(Dispatchers.Default) { productName.isNotEmpty() }
}