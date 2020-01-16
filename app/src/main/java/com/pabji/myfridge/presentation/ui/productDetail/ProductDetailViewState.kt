package com.pabji.myfridge.presentation.ui.productDetail

import com.pabji.myfridge.presentation.models.Product

sealed class ProductDetailViewState
object Loading : ProductDetailViewState()
class ShowProduct(val product: Product) : ProductDetailViewState()
class ShowSaved(val product: Product) : ProductDetailViewState()
object ShowError : ProductDetailViewState()
