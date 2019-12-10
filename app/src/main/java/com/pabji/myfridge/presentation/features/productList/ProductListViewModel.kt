package com.pabji.myfridge.presentation.features.productList

import androidx.lifecycle.LiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.extensions.toProductDTO
import com.pabji.myfridge.presentation.extensions.toProductListLiveData
import com.pabji.myfridge.presentation.models.Product
import kotlinx.coroutines.launch

class ProductListViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<Product>> =
        productRepository.getAll().toProductListLiveData()

    fun onProductClicked(product: Product) {
        launch {
            productRepository.remove(product.toProductDTO())
        }
    }

}