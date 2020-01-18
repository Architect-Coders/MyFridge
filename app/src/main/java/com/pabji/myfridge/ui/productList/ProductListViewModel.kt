package com.pabji.myfridge.ui.productList

import androidx.lifecycle.LiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.data.repository.ProductRepository
import com.pabji.myfridge.presentation.extensions.toProductListLiveData
import com.pabji.myfridge.ui.common.uiModels.ItemProductList

class ProductListViewModel(productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<ItemProductList>> =
        productRepository.getAll().toProductListLiveData()

}