package com.pabji.myfridge.ui.common

import androidx.fragment.app.Fragment
import com.pabji.myfridge.ui.common.extensions.hideKeyboard

abstract class BaseFragment : Fragment() {

    protected fun finish() {
        activity?.run {
            hideKeyboard()
            onBackPressed()
        }
    }
}