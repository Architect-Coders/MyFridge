package com.pabji.myfridge.common

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.pabji.myfridge.MyApp
import com.pabji.myfridge.common.extensions.hideKeyboard

abstract class BaseFragment : Fragment() {
    val app = MyApp.app

    protected val navController by lazy { findNavController() }

    protected fun finish() {
        activity?.run {
            hideKeyboard()
            onBackPressed()
        }
    }
}