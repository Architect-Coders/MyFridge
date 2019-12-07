package com.pabji.myfridge.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pabji.myfridge.R
import com.pabji.myfridge.common.getViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = getViewModel { MainViewModel() }.also {
            it.navigator = MainNavigator(this)
        }
        fab.setOnClickListener { viewModel.onFabClicked() }
    }
}
