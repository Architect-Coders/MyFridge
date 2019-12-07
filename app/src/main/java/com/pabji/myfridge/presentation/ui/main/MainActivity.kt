package com.pabji.myfridge.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pabji.myfridge.R
import com.pabji.myfridge.common.getViewModel
import com.pabji.myfridge.data.datasources.ProductDBDatasource
import com.pabji.myfridge.presentation.ui.productList.ProductListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = getViewModel { MainViewModel(ProductDBDatasource(application)) }

        addFragment(ProductListFragment.newInstance(), true, ProductListFragment.TAG)

        fab.setOnClickListener { viewModel.onFabClicked() }
    }

    fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.fragment_container, fragment, tag)
        ft.commitAllowingStateLoss()
    }
}
