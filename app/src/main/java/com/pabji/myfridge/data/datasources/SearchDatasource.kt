package com.pabji.myfridge.data.datasources

import com.pabji.myfridge.data.extensions.toProductDTOList
import com.pabji.myfridge.data.network.ApiClient
import com.pabji.myfridge.data.network.ApiService
import com.pabji.myfridge.domain.repositories.SearchRepository

class SearchDatasource : SearchRepository {

    private var client: ApiService = ApiClient.createService()

    override suspend fun getRandomProducts() = client.getRamdomProducts().toProductDTOList()
}