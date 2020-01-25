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

    override suspend fun searchProducts(
        searchTerm: String?,
        page: Int
    ): Either<DomainError, List<Product>> {

        val localProducts = localDataSource.getProductsByTerm(searchTerm)

        val remoteProducts = remoteDataSource.searchProducts(searchTerm, page).map {
            it.filter { remoteProduct ->
                remoteProduct.barcode !in localProducts.getBarcodeList()
            }
        }
        return localProducts.flatMap { localProductList ->
            remoteProducts.map { remoteProductList ->
                localProductList + remoteProductList
            }
        }
    }

    override suspend fun getProductListByBarcodeList(barcodeList: List<String>) =
        localDataSource.getProductListByBarcodeList(barcodeList)

    override suspend fun getProductById(productId: Long) = localDataSource.getProductById(productId)

    override suspend fun getProductByBarcode(barcode: String) =
        with(localDataSource.getProductByBarcode(barcode)) {
            if (isLeft) {
                remoteDataSource.getProductByBarcode(barcode)
            } else {
                this
            }
        }

    private fun Either<*, List<Product>>.getBarcodeList() =
        fold({ emptyList<String>() }, { productList ->
            productList.map { product -> product.barcode }
        })
}