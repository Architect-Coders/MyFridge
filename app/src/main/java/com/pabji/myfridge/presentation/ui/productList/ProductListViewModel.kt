package com.pabji.myfridge.presentation.ui.productList

import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.utils.toProductDTO
import com.pabji.myfridge.presentation.utils.toProductListLiveData
import kotlinx.coroutines.launch

class ProductListViewModel(private val productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<Product>> =
        productRepository.getAll().toProductListLiveData()

    fun onFabClick(view: View) {
        launch {
            Navigation.findNavController(view)
                .navigate(R.id.action_productListFragment_to_createProductFragment)
        }
    }

    fun onProductClicked(product: Product) {
        launch {
            productRepository.remove(product.toProductDTO())
        }
    }

}