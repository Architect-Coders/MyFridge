package com.pabji.myfridge.presentation.models

import arrow.core.Option

data class Product(val id: Option<Long>, val name: String = "", val previewUrl: String = "")