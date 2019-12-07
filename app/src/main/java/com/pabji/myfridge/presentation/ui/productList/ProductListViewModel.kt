package com.pabji.myfridge.presentation.ui.productList

import androidx.lifecycle.LiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.utils.toProductListLiveData

class ProductListViewModel(productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<Product>> =
        productRepository.getAllProducts().toProductListLiveData()

}