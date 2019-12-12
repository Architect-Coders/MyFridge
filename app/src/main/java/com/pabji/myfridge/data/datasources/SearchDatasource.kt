package com.pabji.myfridge.data.datasources

import arrow.core.Either
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.extensions.either.bifunctor.bimap
import arrow.integrations.retrofit.adapter.unwrapBody
import com.pabji.myfridge.data.extensions.toProductDTOList
import com.pabji.myfridge.data.network.ApiClient
import com.pabji.myfridge.data.network.ApiService
import com.pabji.myfridge.domain.errors.SearchError
import com.pabji.myfridge.domain.repositories.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchDatasource : SearchRepository {

    private var client: ApiService = ApiClient.createService()
    override suspend fun searchProductsByName(searchTerm: String, page: Int) =
        withContext(Dispatchers.IO) {
            client.searchProductsByName(searchTerm, page)
                .unwrapBody(Either.applicativeError())
                .bimap({ SearchError }, { it.toProductDTOList() })
        }
}