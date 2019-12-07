package com.pabji.myfridge.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pabji.myfridge.R
import com.pabji.myfridge.common.getViewModel
import com.pabji.myfridge.common.setFragment
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

        setFragment(ProductListFragment.newInstance(), R.id.fragment_container)

        fab.setOnClickListener { viewModel.onFabClicked() }
    }
}
