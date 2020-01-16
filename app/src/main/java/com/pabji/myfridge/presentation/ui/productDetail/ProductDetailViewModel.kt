package com.pabji.myfridge.presentation.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.data.repository.ProductRepository
import com.pabji.myfridge.presentation.extensions.toProduct
import com.pabji.myfridge.presentation.extensions.toProductDTO
import com.pabji.myfridge.presentation.models.Product
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    var product: Product?,
    private val productRepository: ProductRepository
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ProductDetailViewState>()
    var viewState: LiveData<ProductDetailViewState> = _viewState

    init {
        product?.let { loadProduct(it) }
    }

    private fun loadProduct(product: Product) {
        launch {
            _viewState.value = Loading
            with(product) {
                (getProductById(productId) ?: getProductByBarcode(barcode))?.run {
                    _viewState.value = ShowProduct(toProduct())
                } ?: run {
                    _viewState.value = ShowError
                }
            }
        }
    }

    fun onClickButtonAdd() {
        launch {
            when (val value = viewState.value) {
                is ShowProduct -> {
                    productRepository.insert(value.product.toProductDTO())
                    _viewState.value = ShowSaved(value.product)
                }
            }
        }
    }

    private suspend fun getProductById(productId: Long?) =
        productId?.run { productRepository.getProductById(this) }

    private suspend fun getProductByBarcode(barcode: String?) =
        barcode?.run { productRepository.getProductDetailByBarcode(this) }
}