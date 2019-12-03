package com.pabji.myfridge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pabji.myfridge.data.database.daos.ProductDao
import com.pabji.myfridge.data.database.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: MyDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MyDatabase::class.java, "myFridge.db"
        ).build()
    }
}