package com.pabji.myfridge.model.network

import com.pabji.data.datasources.RemoteDatasource
import com.pabji.domain.DetailError
import com.pabji.domain.Either
import com.pabji.domain.SearchError
import com.pabji.myfridge.model.network.api.DETAIL_FIELDS
import com.pabji.myfridge.model.network.api.RetrofitApiClient
import com.pabji.myfridge.model.network.api.SIMPLE_FIELDS
import com.pabji.myfridge.model.network.responses.toProduct

class RetrofitDataSource(private val apiClient: RetrofitApiClient) : RemoteDatasource {

    override suspend fun searchProducts(searchTerm: String?) =
        with(
            apiClient.service.searchProductsByName(
                searchTerm,
                fields = SIMPLE_FIELDS.joinToString(",")
            )
        ) {
            if (isSuccessful) {
                Either.Right(body()?.products?.map { it.toProduct() } ?: emptyList())
            } else {
                Either.Left(SearchError)
            }
        }

    override suspend fun getProductByBarcode(barcode: String) =
        with(apiClient.service.getProductDetailById(barcode, DETAIL_FIELDS.joinToString(","))) {
            if (isSuccessful) {
                body()?.product?.run {
                    Either.Right(toProduct())
                } ?: Either.Left(DetailError)
            } else {
                Either.Left(DetailError)
            }
        }
}
