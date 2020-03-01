package com.pabji.myfridge.ui.productList

import com.pabji.domain.DomainError
import com.pabji.myfridge.model.ItemProduct

sealed class ProductListViewState
object Loading : ProductListViewState()
class ShowProductList(val list: List<ItemProduct>) : ProductListViewState()
class ShowError(val error: DomainError) : ProductListViewState()