package com.pabji.myfridge.ui.searchProducts

import com.pabji.myfridge.domain.errors.DomainError
import com.pabji.myfridge.ui.common.uiModels.ItemProductList

sealed class SearchProductsViewState
object Loading : SearchProductsViewState()
class ShowProductList(val list: List<ItemProductList>) : SearchProductsViewState()
class ShowError(val error: DomainError) : SearchProductsViewState()