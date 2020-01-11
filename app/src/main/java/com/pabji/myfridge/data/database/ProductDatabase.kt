package com.pabji.myfridge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pabji.myfridge.data.database.daos.ProductDao
import com.pabji.myfridge.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 2)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}