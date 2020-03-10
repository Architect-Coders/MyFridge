package com.pabji.myfridge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pabji.myfridge.ui.common.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher

class MainActivityViewModel(
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> = _model

    sealed class UiModel {
        object ShowSearch : UiModel()
        object ShowFridge : UiModel()
    }
}
