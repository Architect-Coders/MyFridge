package com.pabji.myfridge.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.Product
import com.pabji.domain.fold
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private var itemProduct: ItemProduct?,
    private val getProductDetail: GetProductDetail,
    private val saveProduct: SaveProduct,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) loadProduct(itemProduct)
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val product: Product) : UiModel()
        data class ProductSaved(val product: Product) : UiModel()
        object Error : UiModel()
    }

    private fun loadProduct(product: ItemProduct?) {
        launch {
            _model.value = UiModel.Loading
            _model.value = product?.run {
                getProductDetail(toProduct()).fold({
                    UiModel.Error
                }, {
                    UiModel.Content(it)
                })
            } ?: UiModel.Error
        }
    }

    fun onClickButtonAdd() {
        launch {
            when (val value = _model.value) {
                is UiModel.Content -> {
                    saveProduct(value.product)
                    _model.value = UiModel.ProductSaved(value.product)
                }
            }
        }
    }
}
