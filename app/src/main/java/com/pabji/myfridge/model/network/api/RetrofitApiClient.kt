package com.pabji.myfridge.model.network.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiClient(baseUrl: String) {

    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

    val service = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(RetrofitApiService::class.java)
}
