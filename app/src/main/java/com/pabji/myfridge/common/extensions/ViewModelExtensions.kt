package com.pabji.myfridge.common.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T) =
    ViewModelProviders.of(this, createViewModelFactory(factory))[T::class.java]

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T) =
    ViewModelProviders.of(this, createViewModelFactory(factory))[T::class.java]

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> createViewModelFactory(crossinline factory: () -> T) =
    object : ViewModelProvider.Factory {
        override fun <U : ViewModel?> create(modelClass: Class<U>): U = factory() as U
    }