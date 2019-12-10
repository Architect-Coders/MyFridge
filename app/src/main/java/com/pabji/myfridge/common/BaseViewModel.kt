package com.pabji.myfridge.common

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(), Scope by Scope.Impl() {

    init {
        this.initScope()
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}