package com.pabji.myfridge.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.pabji.myfridge.R
import com.pabji.myfridge.common.BaseFragment
import com.pabji.myfridge.presentation.adapters.MainViewPagerAdapter
import com.pabji.myfridge.presentation.fragments.productList.ProductListFragment
import com.pabji.myfridge.presentation.fragments.searchProducts.SearchProductsFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setBottomNavigation()
        setFab()
    }

    private fun setViewPager() {
        vp_container.run {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 2
            adapter = MainViewPagerAdapter(childFragmentManager, lifecycle).apply {
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
        fab.setOnClickListener {
            MainFragmentDirections.actionMainFragmentToCreateProductFragment().run {
                navController.navigate(this)
            }
        }
    }

    private fun onPagePositionSelected(position: Int) {
        when (position) {
            PRODUCT_LIST_POSITION -> {
                fab.show()
            }
            SEARCH_POSITION -> {
                fab.hide()
            }
        }
    }

    companion object {
        const val PRODUCT_LIST_POSITION = 0
        const val SEARCH_POSITION = 1
    }
}
