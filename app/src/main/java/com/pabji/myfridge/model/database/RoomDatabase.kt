package com.pabji.myfridge.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pabji.myfridge.model.database.daos.ProductDao
import com.pabji.myfridge.model.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}