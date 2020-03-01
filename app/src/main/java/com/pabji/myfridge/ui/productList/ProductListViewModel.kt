package com.pabji.myfridge.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.Event
import com.pabji.usecases.GetMyProducts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val getMyProducts: GetMyProducts,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private val _navigation = MutableLiveData<Event<ItemProduct>>()
    val navigation: LiveData<Event<ItemProduct>> = _navigation

    sealed class UiModel {
        data class Content(val list: List<ItemProduct>) : UiModel()
        object EmptyList : UiModel()
    }

    fun updateData() {
        launch {
            _model.value = getMyProducts().let { result ->
                if (result.isEmpty()) {
                    UiModel.EmptyList
                } else {
                    UiModel.Content(result.map { product -> product.toItemProduct() })
                }
            }
        }
    }

    fun onProductClicked(product: ItemProduct) {
        _navigation.value = Event(product)
    }

}