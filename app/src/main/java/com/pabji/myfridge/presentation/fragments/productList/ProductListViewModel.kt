package com.pabji.myfridge.presentation.fragments.productList

import androidx.lifecycle.LiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.extensions.toProductListLiveData
import com.pabji.myfridge.presentation.models.Product

class ProductListViewModel(productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<Product>> = productRepository.getAll().toProductListLiveData()

}