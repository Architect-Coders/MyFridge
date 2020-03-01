package com.pabji.myfridge.ui.searchProducts

import com.pabji.domain.DomainError
import com.pabji.myfridge.model.ItemProduct

sealed class SearchProductsViewState
object Loading : SearchProductsViewState()
class ShowProductList(val list: List<ItemProduct>) : SearchProductsViewState()
class ShowError(val error: DomainError) : SearchProductsViewState()