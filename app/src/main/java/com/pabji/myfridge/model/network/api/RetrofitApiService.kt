package com.pabji.myfridge.model.network.api

import com.pabji.myfridge.model.network.responses.ProductDetailResponse
import com.pabji.myfridge.model.network.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApiService {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process")
    suspend fun searchProductsByName(
        @Query("search_terms") name: String? = null,
        @Query("page") page: Int = 1,
        @Query("fields") fields: String
    ): Response<SearchResponse>

    @GET("api/v0/product/{id}.json")
    suspend fun getProductDetailById(
        @Path("id") id: String,
        @Query("fields") fields: String
    ): Response<ProductDetailResponse>
}
