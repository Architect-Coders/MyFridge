package com.pabji.myfridge.ui.productList

import com.pabji.domain.DomainError
import com.pabji.myfridge.ui.common.uiModels.ItemProductList

sealed class ProductListViewState
object Loading : ProductListViewState()
class ShowProductList(val list: List<ItemProductList>) : ProductListViewState()
class ShowError(val error: DomainError) : ProductListViewState()