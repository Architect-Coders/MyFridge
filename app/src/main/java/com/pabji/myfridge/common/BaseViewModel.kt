package com.pabji.myfridge.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pabji.myfridge.domain.errors.DomainError

open class BaseViewModel : ViewModel(), Scope by Scope.Impl() {

    private val _errorState = MutableLiveData<DomainError>()
    val errorState: LiveData<DomainError> = _errorState

    init {
        this.initScope()
    }

    protected fun onErrorResult(error: DomainError) {
        _errorState.value = error
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}