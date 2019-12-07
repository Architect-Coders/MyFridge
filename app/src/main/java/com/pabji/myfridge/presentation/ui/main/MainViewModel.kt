package com.pabji.myfridge.presentation.ui.main

import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.utils.toProductDTOList
import kotlinx.coroutines.launch

class MainViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    fun onFabClicked() {
        launch {
            val productListSize = productRepository.getAllProducts().value?.size ?: 0
            val productList =
                ((productListSize + 1)..(productListSize + 10)).map {
                    Product(
                        "Product $it"
                    )
                }
            productRepository.insertAll(productList.toProductDTOList())
        }
    }
}