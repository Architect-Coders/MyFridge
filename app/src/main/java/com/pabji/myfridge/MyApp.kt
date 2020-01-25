package com.pabji.myfridge

import android.app.Application
import androidx.room.Room
import com.pabji.myfridge.model.database.RoomDatabase
import com.pabji.myfridge.model.network.api.RetrofitApiClient
import com.pabji.myfridge.model.network.api.RetrofitApiService

class MyApp : Application() {

    lateinit var db: RoomDatabase
        private set

    lateinit var apiService: RetrofitApiService
        private set

    override fun onCreate() {
        super.onCreate()
        app = this
        db = Room.databaseBuilder(
                this,
            RoomDatabase::class.java, "products.db"
        ).fallbackToDestructiveMigration()
            .build()

        apiService = RetrofitApiClient.createService()
    }

    companion object {
        lateinit var app: MyApp
    }
}