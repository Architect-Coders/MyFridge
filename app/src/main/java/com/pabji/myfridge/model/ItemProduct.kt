package com.pabji.myfridge.model

import com.pabji.domain.Product
import java.io.Serializable

data class ItemProduct(
    val name: String = "",
    val previewUrl: String = "",
    val existInFridge: Boolean = false,
    val barcode: String = ""
) : Serializable

fun Product.toItemProduct(): ItemProduct =
    ItemProduct(
        name,
        previewUrl,
        existInFridge,
        barcode
    )

fun ItemProduct.toProduct(): Product =
    Product(name = name, barcode = barcode)
