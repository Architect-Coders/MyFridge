package com.pabji.myfridge.ui.productDetail

sealed class ProductDetailViewState
object Loading : ProductDetailViewState()
class ShowProduct(val product: ProductDetail) : ProductDetailViewState()
class ShowSaved(val product: ProductDetail) : ProductDetailViewState()
object ShowError : ProductDetailViewState()
