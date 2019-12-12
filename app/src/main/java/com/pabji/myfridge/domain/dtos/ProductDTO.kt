package com.pabji.myfridge.domain.dtos

import arrow.core.None
import arrow.core.Option

data class ProductDTO(
    val id: Option<Long> = None,
    val name: Option<String> = None,
    val previewUrl: Option<String> = None
)