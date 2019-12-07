package com.pabji.myfridge.common

import androidx.fragment.app.Fragment
import com.pabji.myfridge.MyApp

abstract class BaseFragment : Fragment() {
    val app = MyApp.app
}