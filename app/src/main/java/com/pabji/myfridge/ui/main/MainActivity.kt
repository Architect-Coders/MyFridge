package com.pabji.myfridge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.pabji.myfridge.R
import com.pabji.myfridge.ui.barcode.BarcodeReaderActivity
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.productList.ProductListFragment
import com.pabji.myfridge.ui.searchProducts.SearchProductsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setViewPager()
        setBottomNavigation()
        setFab()
    }

    private fun setViewPager() {
        vp_container.run {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 2
            adapter = MainViewPagerAdapter(
                supportFragmentManager,
                lifecycle
            ).apply {
                list = listOf(
                    ProductListFragment.newInstance(),
                    SearchProductsFragment.newInstance()
                )
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    onPagePositionSelected(position)
                }
            })
        }
    }

    private fun setBottomNavigation() {
        bottom_navigation.let {
            it.setOnNavigationItemSelectedListener { item ->
                vp_container.currentItem = when (item.itemId) {
                    R.id.action_fridge -> PRODUCT_LIST_POSITION
                    else -> SEARCH_POSITION
                }
                true
            }
        }
    }

    private fun setFab() {
        fab.setOnClickListener { startActivity<BarcodeReaderActivity> {} }
    }

    private fun onPagePositionSelected(position: Int) {
        when (position) {
            PRODUCT_LIST_POSITION -> fab.show()
            SEARCH_POSITION -> fab.hide()
        }
    }

    companion object {
        const val PRODUCT_LIST_POSITION = 0
        const val SEARCH_POSITION = 1
    }
}
