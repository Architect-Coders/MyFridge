package com.pabji.myfridge.model

import com.pabji.domain.Product
import java.io.Serializable

data class ItemProduct(
    val id: Long?,
    val name: String,
    val previewUrl: String,
    val existInFridge: Boolean,
    val barcode: String
) : Serializable

fun Product.toItemProduct(): ItemProduct =
    ItemProduct(
        id,
        name,
        previewUrl,
        existInFridge,
        barcode
    )

fun ItemProduct.toProduct(): Product =
    Product(id = id, name = name, barcode = barcode)