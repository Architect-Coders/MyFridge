package com.pabji.myfridge.presentation.ui.searchProducts

import com.pabji.myfridge.presentation.models.Product

sealed class SearchProductsViewState
object SearchError : SearchProductsViewState()
class SearchResult(val productList: List<Product>) : SearchProductsViewState()