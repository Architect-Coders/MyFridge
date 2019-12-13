package com.pabji.myfridge

import android.app.Application
import androidx.room.Room
import com.pabji.myfridge.data.database.ProductDatabase

class MyApp : Application() {

    lateinit var db: ProductDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        app = this
        db = Room.databaseBuilder(
                this,
                ProductDatabase::class.java, "myFridge.db"
        ).build()
    }

    companion object {
        lateinit var app: MyApp
    }
}