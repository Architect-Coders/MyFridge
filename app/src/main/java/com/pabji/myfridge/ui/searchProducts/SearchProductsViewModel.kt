package com.pabji.myfridge.ui.searchProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.Event
import com.pabji.usecases.SearchProductsByTerm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class SearchProductsViewModel(
    private val searchProductsByTerm: SearchProductsByTerm,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    private val _navigation = MutableLiveData<Event<ItemProduct>>()
    val navigation: LiveData<Event<ItemProduct>> = _navigation

    sealed class UiModel {
        object Loading : UiModel()
        data class Content(val list: List<ItemProduct>) : UiModel()
        object EmptyList : UiModel()
    }

    fun onSearch(searchTerm: String = "") {
        launch {
            _model.value = UiModel.Loading
            searchProductsByTerm(searchTerm).let { result ->
                _model.value = if (result.isEmpty()) {
                    UiModel.EmptyList
                } else {
                    UiModel.Content(result.map { it.toItemProduct() })
                }
            }
        }
    }

    fun onProductClicked(product: ItemProduct) {
        _navigation.value = Event(product)
    }
}
