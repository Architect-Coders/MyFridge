package com.pabji.myfridge.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.pabji.myfridge.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        viewModel.viewState.observe(this, Observer(::updateUI))

        fab.setOnClickListener { viewModel.onFabClicked() }
    }

    private fun updateUI(viewState: MainViewState?) {
        when (viewState) {
            SnackBar -> showSnackBar()
        }
    }

    private fun showSnackBar() {
        Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }


}
