package com.pabji.myfridge.ui.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle


import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import com.pabji.myfridge.ui.common.BaseFragment

inline fun <reified T : BaseFragment> AppCompatActivity.setFragment(
    fragment: T, @IntegerRes fragmentContainer: Int,
    addToBackStack: Boolean = false
) {

    val tag = T::class.simpleName
    supportFragmentManager.beginTransaction().run {
        if (addToBackStack) {
            addToBackStack(tag)
        }
        replace(fragmentContainer, fragment, tag)
        commitAllowingStateLoss()
    }
}


fun Activity.showKeyboard(editText: EditText) {
    editText.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, 0)
}

fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

inline fun <reified T : Any> Activity.startActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {}
) {

    val intent = newIntent<T>(this)
    intent.init()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        startActivityForResult(intent, requestCode, options)
    } else {
        startActivityForResult(intent, requestCode)
    }
}

inline fun <reified T : Any> newIntent(activity: Activity): Intent = Intent(activity, T::class.java)