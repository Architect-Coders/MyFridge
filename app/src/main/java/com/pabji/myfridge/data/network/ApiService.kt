package com.pabji.myfridge.data.network

import com.pabji.myfridge.data.network.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,image_small_url")
    suspend fun searchProductsByName(@Query("search_terms") name: String, @Query("page") page: Int): Response<SearchResponse>

}