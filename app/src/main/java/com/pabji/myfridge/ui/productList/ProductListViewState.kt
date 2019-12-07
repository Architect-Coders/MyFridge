package com.pabji.myfridge.ui.productList

sealed class MainViewState
object Loading : MainViewState()
class MyProductList(val productList: List<Product>) : MainViewState()