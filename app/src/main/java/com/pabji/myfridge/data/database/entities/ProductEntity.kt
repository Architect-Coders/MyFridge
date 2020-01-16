package com.pabji.myfridge.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val barcode: String?,
    val name: String?,
    val previewUrl: String?,
    val imageUrl: String?,
    val quantity: String?,
    val stores: String?,
    val countries: String?,
    val imageNutritionUrl: String?,
    val brands: String?,
    val genericName: String?,
    val ingredientsText: String?,
    val imageIngredientsUrl: String?,
    val categories: String?
)