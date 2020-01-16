package com.pabji.myfridge.data.datasources

import com.pabji.myfridge.data.network.ApiClient
import com.pabji.myfridge.data.network.ApiService
import com.pabji.myfridge.data.network.DETAIL_FIELDS
import com.pabji.myfridge.data.network.SIMPLE_FIELDS
import com.pabji.myfridge.data.utils.toProductDTO
import com.pabji.myfridge.data.utils.toProductDTOList

class ProductNetworkDatasourceImpl : ProductNetworkDatasource {

    private var client: ApiService = ApiClient.createService()
    override suspend fun searchProducts(searchTerm: String?, page: Int) =
        client.searchProductsByName(
            searchTerm,
            page,
            SIMPLE_FIELDS.joinToString(",")
        )?.toProductDTOList() ?: emptyList()

    override suspend fun getProductByBarcode(barcode: String) =
        client.getProductDetailById(barcode, DETAIL_FIELDS.joinToString(","))?.toProductDTO()
}