package com.pabji.data.repositories

import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.domain.*

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

    override suspend fun getProductDetail(product: Product): Either<DomainError, Product> =
        with(localDataSource.getProductById(product.id ?: 0)) {
            if (isLeft) {
                val barcode = product.barcode
                with(localDataSource.getProductByBarcode(barcode)) {
                    if (isLeft) {
                        remoteDataSource.getProductByBarcode(barcode)
                    } else {
                        this
                    }
                }
            } else {
                this
            }
        }

    override suspend fun searchProductsByBarcode(barcodeList: List<String>): List<Product> =
        barcodeList
            .mapNotNull {
                remoteDataSource.getProductByBarcode(it).fold({ null }) { product -> product }
            }
}

internal fun List<Product>.getFilteredProductsByProducts(filteredProducts: List<Product>): List<Product> {
    val barcodeList = filteredProducts.map { product -> product.barcode }
    return filter { remoteProduct -> remoteProduct.barcode !in barcodeList }
}