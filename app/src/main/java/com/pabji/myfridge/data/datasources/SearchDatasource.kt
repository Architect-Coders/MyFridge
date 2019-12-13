package com.pabji.myfridge.data.datasources

import arrow.core.Either
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.either.bifunctor.bimap
import arrow.integrations.retrofit.adapter.unwrapBody
import com.pabji.myfridge.data.extensions.toProductDTO
import com.pabji.myfridge.data.extensions.toProductDTOList
import com.pabji.myfridge.data.network.ApiClient
import com.pabji.myfridge.data.network.ApiService
import com.pabji.myfridge.domain.errors.DetailError
import com.pabji.myfridge.domain.errors.SearchError
import com.pabji.myfridge.domain.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class SearchDatasource : SearchRepository {

    private var client: ApiService = ApiClient.createService()
    override suspend fun searchProductsByName(searchTerm: String, page: Int) =
            getHTTPResponse(client.searchProductsByName(searchTerm, page))
                    .bimap({ SearchError }, { it.toProductDTOList() })

    override suspend fun getProductDetail(productId: String) =
            getHTTPResponse(client.getProductDetailById(productId))
                    .bimap({ DetailError }, { it.toProductDTO() })


    private suspend fun <T, R : Response<T>> getHTTPResponse(response: R) =
            withContext(Dispatchers.IO) { response.unwrapBody(Either.applicativeError()) }
}