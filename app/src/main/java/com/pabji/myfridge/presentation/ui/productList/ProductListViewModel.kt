package com.pabji.myfridge.presentation.ui.productList

import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseViewModel
import com.pabji.myfridge.domain.repositories.ProductRepository
import com.pabji.myfridge.presentation.models.Product
import com.pabji.myfridge.presentation.utils.toProductListLiveData

class ProductListViewModel(productRepository: ProductRepository) : BaseViewModel() {

    val productList: LiveData<List<Product>> =
        productRepository.getAll().toProductListLiveData()

    fun onFabClick(view: View) {
        Navigation.findNavController(view)
            .navigate(R.id.action_productListFragment_to_createProductFragment)
    }

}