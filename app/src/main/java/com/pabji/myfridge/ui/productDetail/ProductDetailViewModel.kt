package com.pabji.myfridge.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.fold
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    var product: ItemProduct?,
    private val getProductDetail: GetProductDetail,
    private val saveProduct: SaveProduct,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _viewState = MutableLiveData<ProductDetailViewState>()
    var viewState: LiveData<ProductDetailViewState> = _viewState

    init {
        product?.let { loadProduct(it) }
    }

    private fun loadProduct(product: ItemProduct) {
        launch {
            _viewState.value = Loading
            with(product) {
                getProductDetail(toProduct()).fold({
                    _viewState.value = ShowError
                }, {
                    _viewState.value = ShowProduct(it.toProductDetail())
                })
            }
        }
    }

    fun onClickButtonAdd() {
        launch {
            when (val value = viewState.value) {
                is ShowProduct -> {
                    saveProduct(value.product.toProduct())
                    _viewState.value = ShowSaved(value.product)
                }
            }
        }
    }
}