package com.pabji.myfridge.presentation.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.pabji.myfridge.R
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.presentation.adapters.MainViewPagerAdapter
import com.pabji.myfridge.presentation.features.productList.ProductListFragment
import com.pabji.myfridge.presentation.features.searchProducts.SearchProductsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_LIST_POSITION = 0
        const val SEARCH_POSITION = 1
    }

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = getViewModel { MainViewModel() }.also {
            it.navigator = MainNavigator(this)
        }
        vp_container.let {
            it.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            it.adapter = MainViewPagerAdapter(
                this, listOf(
                    ProductListFragment.newInstance(),
                    SearchProductsFragment.newInstance()
                )
            )
            it.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onPagePositionSelected(position)
                }
            })
        }
        bottom_navigation.let {
            it.setOnNavigationItemSelectedListener { item ->
                vp_container.currentItem = when (item.itemId) {
                    R.id.action_fridge -> PRODUCT_LIST_POSITION
                    else -> SEARCH_POSITION
                }
                true
            }
        }

        fab.setOnClickListener { viewModel.onFabClick() }
    }

    private fun onPagePositionSelected(position: Int) {
        when (position) {
            PRODUCT_LIST_POSITION -> fab.show()
            SEARCH_POSITION -> fab.hide()
        }
    }
}
