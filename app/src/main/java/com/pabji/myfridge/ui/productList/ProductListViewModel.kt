package com.pabji.myfridge.ui.productList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.domain.fold
import com.pabji.myfridge.ui.common.BaseViewModel
import com.pabji.myfridge.ui.common.uiModels.ItemProductList
import com.pabji.myfridge.ui.common.uiModels.toItemProduct
import com.pabji.usecases.GetMyProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductListViewModel(private val getMyProducts: GetMyProducts) : BaseViewModel() {

    private val _productList = MutableLiveData<List<ItemProductList>>()
    val productList: LiveData<List<ItemProductList>> = _productList

    fun getProductList() {
        launch {
            withContext(Dispatchers.IO) {
                getMyProducts().fold({

                }, {
                    _productList.postValue(it.map { product -> product.toItemProduct() })
                })
            }
        }
    }

}