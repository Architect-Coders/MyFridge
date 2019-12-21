package com.pabji.myfridge.data.datasources

import arrow.core.Either
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.either.bifunctor.bimap
import arrow.integrations.retrofit.adapter.unwrapBody
import com.pabji.myfridge.data.network.*
import com.pabji.myfridge.data.utils.toProductDTO
import com.pabji.myfridge.data.utils.toProductDTOList
import com.pabji.myfridge.domain.errors.DetailError
import com.pabji.myfridge.domain.errors.SearchError
import com.pabji.myfridge.domain.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProductNetworkDatasource : SearchRepository {

    private var client: ApiService = ApiClient.createService()

    override suspend fun getRandomProducts() =
        getHTTPResponse(client.searchProductsByName(fields = SIMPLE_FIELDS.joinToString(",")))
            .bimap({ SearchError }, { it.toProductDTOList() })

    override suspend fun searchProductsByName(searchTerm: String, page: Int) =
        getHTTPResponse(
            client.searchProductsByName(
                searchTerm,
                page,
                SIMPLE_FIELDS.joinToString(",")
            )
        )
                    .bimap({ SearchError }, { it.toProductDTOList() })

    override suspend fun getProductDetail(productId: String) =
        getHTTPResponse(client.getProductDetailById(productId, DETAIL_FIELDS.joinToString(",")))
                    .bimap({ DetailError }, { it.toProductDTO() })


    private suspend fun <T, R : Response<T>> getHTTPResponse(response: R) =
            withContext(Dispatchers.IO) { response.unwrapBody(Either.applicativeError()) }

    companion object {
        val SIMPLE_FIELDS = listOf(PRODUCT_NAME, IMAGE_SMALL_URL, QUANTITY)
        val DETAIL_FIELDS = listOf(
            SIMPLE_FIELDS,
            CODE,
            STORES,
            COUNTRIES,
            IMAGE_NUTRITION_URL,
            GENERIC_NAME,
            INGREDIENTS_TEXT,
            IMAGE_INGREDIENTS_URL,
            CATEGORIES,
            BRANDS
        )
    }
}