package com.pabji.myfridge.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pabji.domain.Product
import com.pabji.myfridge.model.common.extensions.getListByDelimit

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val barcode: String,
    val name: String,
    val previewUrl: String,
    val imageUrl: String,
    val quantity: String,
    val stores: String,
    val countries: String,
    val imageNutritionUrl: String,
    val brands: String,
    val genericName: String,
    val ingredientsText: String,
    val imageIngredientsUrl: String,
    val categories: String
)

internal fun ProductEntity.toProduct() =
    Product(
        id,
        barcode,
        name,
        previewUrl,
        imageUrl,
        quantity,
        stores,
        countries.getListByDelimit(","),
        imageNutritionUrl,
        brands.getListByDelimit(","),
        genericName,
        ingredientsText,
        imageIngredientsUrl,
        categories,
        existInFridge = true
    )

internal fun Product.toProductEntity() = ProductEntity(
    id,
    barcode,
    name,
    previewUrl,
    imageUrl,
    quantity,
    stores,
    countries.joinToString(),
    imageNutritionUrl,
    brands.joinToString(),
    genericName,
    ingredientsText,
    imageIngredientsUrl,
    categories
)