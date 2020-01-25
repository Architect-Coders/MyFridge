package com.pabji.myfridge.model.common.extensions

fun String.getListByDelimit(delimit: String) = run { split(delimit).map { it.trim() } }