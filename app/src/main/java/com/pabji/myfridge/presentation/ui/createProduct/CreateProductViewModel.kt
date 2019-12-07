package com.pabji.myfridge.presentation.ui.createProduct

import androidx.lifecycle.LiveData
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.utils.toProductDTOList
import com.pabji.myfridge.presentation.utils.toProductListLiveData
import kotlinx.coroutines.launch

class CreateProductViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<Product>> =
        productRepository.getAllProducts().toProductListLiveData()

    fun onFabClicked() {
        launch {
            val productListSize = productList.value?.size ?: 0
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