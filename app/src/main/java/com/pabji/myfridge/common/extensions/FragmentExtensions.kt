package com.pabji.myfridge.common.extensions

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

inline fun <reified T : Any> Fragment.startActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {
    activity?.run {
        val intent = newIntent<T>(this)
        intent.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivityForResult(intent, requestCode, options)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }
}

inline fun <reified T : Any> newIntent(activity: FragmentActivity): Intent =
    Intent(activity, T::class.java)