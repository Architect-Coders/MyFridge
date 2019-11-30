package com.pabji.myfridge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pabji.myfridge.common.Scope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), Scope by Scope.Impl() {

    private val _viewState = MutableLiveData<MainViewState>()
    val viewState: LiveData<MainViewState> get() = _viewState

    init {
        initScope()
    }

    fun onFabClicked() {
        launch {
            _viewState.value = SnackBar
        }
    }

    override fun onCleared() {
        cancelScope()
        super.onCleared()
    }
}