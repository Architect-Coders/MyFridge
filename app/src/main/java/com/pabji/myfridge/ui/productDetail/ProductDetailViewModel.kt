package com.pabji.myfridge.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.fold
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.uiModels.ItemProductList
import com.pabji.myfridge.ui.common.uiModels.toProduct
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    var product: ItemProductList?,
    private val getProductDetail: GetProductDetail,
    private val saveProduct: SaveProduct
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ProductDetailViewState>()
    var viewState: LiveData<ProductDetailViewState> = _viewState

    init {
        product?.let { loadProduct(it) }
    }

    private fun loadProduct(product: ItemProductList) {
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