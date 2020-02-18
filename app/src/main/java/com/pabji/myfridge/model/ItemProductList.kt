package com.pabji.myfridge.model

import com.pabji.domain.Product
import java.io.Serializable

data class ItemProductList(
    val id: Long?,
    val name: String,
    val previewUrl: String,
    val existInFridge: Boolean,
    val barcode: String
) : Serializable

fun Product.toItemProduct(): ItemProductList =
    ItemProductList(
        id,
        name,
        previewUrl,
        existInFridge,
        barcode
    )

fun ItemProductList.toProduct(): Product =
    Product(id = id, name = name, barcode = barcode)