package com.pabji.myfridge.ui.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.Product
import com.pabji.domain.fold
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.RemoveProduct
import com.pabji.usecases.SaveProduct
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private var itemProduct: ItemProduct?,
    private val getProductDetail: GetProductDetail,
    private val saveProduct: SaveProduct,
    private val removeProduct: RemoveProduct,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private var product: Product? = null
    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) loadProduct(itemProduct)
            return _model
        }

    sealed class UiModel {
        data class BasicContent(val product: ItemProduct?) : UiModel()
        data class FullContent(val product: Product) : UiModel()
        data class ProductSaved(val product: Product) : UiModel()
        data class ProductRemoved(val product: Product) : UiModel()
        object Error : UiModel()
    }

    private fun loadProduct(itemProduct: ItemProduct?) {
        launch {
            _model.value = UiModel.BasicContent(itemProduct)
            _model.value = itemProduct?.run {
                getProductDetail(toProduct()).fold({
                    UiModel.Error
                }, {
                    product = it
                    UiModel.FullContent(it)
                })
            } ?: UiModel.Error
        }
    }

    fun onClickButtonAdd() {
        launch {
            product?.run {
                _model.value = when {
                    existInFridge -> {
                        removeProduct(this)
                        existInFridge = false
                        UiModel.ProductRemoved(this)
                    }
                    else -> {
                        saveProduct(this)
                        existInFridge = true
                        UiModel.ProductSaved(this)
                    }
                }
            }
        }
    }
}
