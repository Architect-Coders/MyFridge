package com.pabji.myfridge.data.network

import com.pabji.myfridge.data.network.responses.ProductResponse
import com.pabji.myfridge.data.network.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("cgi/search.pl?search_simple=1&json=1&action=process&fields=product_name,image_small_url,quantity")
    suspend fun searchProductsByName(@Query("search_terms") name: String, @Query("page") page: Int): Response<SearchResponse>

    @GET("api/v0/product/{id}.json?fields=product_name,image_small_url,quantity,code,stores,countries,image_nutrition_url,generic_name,ingredients_text,image_ingredients_url,categories")
    suspend fun getProductDetailById(@Path("id") id: String): Response<ProductResponse>
}