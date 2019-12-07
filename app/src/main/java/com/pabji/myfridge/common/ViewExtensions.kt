package com.pabji.myfridge.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.Adapter<*>.notifyChanges(oldList: List<T>, newList: List<T>, compare: (T, T) -> Boolean) {

    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            compare(oldList[oldItemPosition], newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    })
    diff.dispatchUpdatesTo(this)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel?> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel?> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}