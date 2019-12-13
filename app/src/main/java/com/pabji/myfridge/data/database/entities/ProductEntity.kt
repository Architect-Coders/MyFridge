package com.pabji.myfridge.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
        @PrimaryKey(autoGenerate = true) val id: Long?,
        val name: String?,
        val previewUrl: String?
)