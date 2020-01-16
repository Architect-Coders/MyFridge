package com.pabji.myfridge.presentation.ui.searchProducts

import com.pabji.myfridge.domain.errors.DomainError
import com.pabji.myfridge.presentation.models.Product

sealed class SearchProductsViewState
object Loading : SearchProductsViewState()
class ShowProductList(val list: List<Product>) : SearchProductsViewState()
class ShowError(val error: DomainError) : SearchProductsViewState()