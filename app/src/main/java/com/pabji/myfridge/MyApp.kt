package com.pabji.myfridge

import android.app.Application
import com.pabji.myfridge.di.initDI

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}
