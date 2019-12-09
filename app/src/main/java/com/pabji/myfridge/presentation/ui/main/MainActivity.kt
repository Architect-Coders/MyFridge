package com.pabji.myfridge.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pabji.myfridge.R
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.common.extensions.setFragment
import com.pabji.myfridge.presentation.ui.productList.ProductListFragment
import com.pabji.myfridge.presentation.ui.searchProducts.SearchProductsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = getViewModel { MainViewModel() }
        bottom_navigation.let {
            it.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_fridge -> {
                        setFragment(ProductListFragment.newInstance(), R.id.fragment_container)
                        true
                    }
                    R.id.action_search -> {
                        setFragment(SearchProductsFragment.newInstance(), R.id.fragment_container)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}
