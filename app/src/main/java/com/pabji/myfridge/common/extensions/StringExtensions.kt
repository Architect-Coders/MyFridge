package com.pabji.myfridge.common.extensions

fun String.getListByDelimit(delimit: String) = run { split(delimit).map { it.trim() } }