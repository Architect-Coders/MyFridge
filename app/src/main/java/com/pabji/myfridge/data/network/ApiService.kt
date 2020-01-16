package com.pabji.myfridge.data.network

import com.pabji.myfridge.data.network.responses.ProductDetailResponse
import com.pabji.myfridge.data.network.responses.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process")
    suspend fun searchProductsByName(
        @Query("search_terms") name: String? = null, @Query("page") page: Int, @Query(
            "fields"
        ) fields: String
    ): SearchResponse?

    @GET("api/v0/product/{id}.json")
    suspend fun getProductDetailById(@Path("id") id: String, @Query("fields") fields: String): ProductDetailResponse?

}