package com.pabji.myfridge.presentation.ui.productList

import com.pabji.myfridge.domain.errors.DomainError
import com.pabji.myfridge.presentation.models.Product

sealed class ProductListViewState
object Loading : ProductListViewState()
class ShowProductList(val list: List<Product>) : ProductListViewState()
class ShowError(val error: DomainError) : ProductListViewState()