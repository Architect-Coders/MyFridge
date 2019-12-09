package com.pabji.myfridge.data.network

import com.pabji.myfridge.data.network.responses.SearchResponse
import retrofit2.http.GET

interface ApiService {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process")
    suspend fun getRamdomProducts(): SearchResponse

}