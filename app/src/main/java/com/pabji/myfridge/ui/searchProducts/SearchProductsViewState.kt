package com.pabji.myfridge.ui.searchProducts

import com.pabji.domain.DomainError
import com.pabji.myfridge.model.ItemProductList

sealed class SearchProductsViewState
object Loading : SearchProductsViewState()
class ShowProductList(val list: List<ItemProductList>) : SearchProductsViewState()
class ShowError(val error: DomainError) : SearchProductsViewState()