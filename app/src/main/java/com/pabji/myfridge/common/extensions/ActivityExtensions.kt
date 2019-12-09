package com.pabji.myfridge.common.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import com.pabji.myfridge.common.BaseFragment

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