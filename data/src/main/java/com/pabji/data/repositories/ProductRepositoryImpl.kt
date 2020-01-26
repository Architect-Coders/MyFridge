package com.pabji.data.repositories

import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.domain.Product
import com.pabji.domain.fold
import com.pabji.domain.map

class ProductRepositoryImpl(
    private val localDataSource: LocalDatasource,
    private val remoteDataSource: RemoteDatasource
) : ProductRepository {

    override suspend fun getProducts() = localDataSource.getProductList()

    override suspend fun saveProduct(product: Product) = localDataSource.saveProduct(product)

    override suspend fun removeProduct(product: Product) = localDataSource.removeProduct(product)

    override suspend fun searchProducts(searchTerm: String?): List<Product> {

        val localProducts = localDataSource.getProductsByTerm(searchTerm ?: "")

        val remoteProducts = remoteDataSource.searchProducts(searchTerm)
            .map { it.getFilteredProductsByProducts(localProducts) }

        return remoteProducts.fold({ localProducts },
            { remoteProductList -> localProducts + remoteProductList })
    }

    override suspend fun getProductById(productId: Long) = localDataSource.getProductById(productId)

    override suspend fun getProductByBarcode(barcode: String) =
        with(localDataSource.getProductByBarcode(barcode)) {
            if (isLeft) {
                remoteDataSource.getProductByBarcode(barcode)
            } else {
                this
            }
        }
}

internal fun List<Product>.getFilteredProductsByProducts(filteredProducts: List<Product>): List<Product> {
    val barcodeList = filteredProducts.map { product -> product.barcode }
    return filter { remoteProduct -> remoteProduct.barcode !in barcodeList }
}