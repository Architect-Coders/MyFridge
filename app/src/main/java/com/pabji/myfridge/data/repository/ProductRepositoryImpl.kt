package com.pabji.myfridge.data.repository

import com.pabji.myfridge.data.datasources.ProductDBDatasource
import com.pabji.myfridge.data.datasources.ProductNetworkDatasource
import com.pabji.myfridge.domain.dtos.ProductDTO

class ProductRepositoryImpl(
    private val db: ProductDBDatasource,
    private val networkClient: ProductNetworkDatasource
) : ProductRepository {

    override fun getAll() = db.getAll()

    override suspend fun insertAll(productList: List<ProductDTO>) = db.insertAll(productList)

    override suspend fun insert(product: ProductDTO) = db.insert(product)

    override suspend fun remove(product: ProductDTO) = db.remove(product)

    override suspend fun searchProducts(searchTerm: String?, page: Int): List<ProductDTO> {
        val searchProducts = networkClient.searchProducts(searchTerm, page)
        val dbProducts =
            db.getProductsByBarcode(searchProducts.map { it.barcode ?: "" }).map { it.barcode }

        return searchProducts.map {
            if (it.barcode in dbProducts) {
                it.existInFridge = true
            }
            it
        }
    }

    override suspend fun getProductDetailByBarcode(barcode: String) =
        db.getProductByBarcode(barcode) ?: networkClient.getProductByBarcode(barcode)

    override suspend fun getProductById(productId: Long): ProductDTO? = db.getProductById(productId)
}