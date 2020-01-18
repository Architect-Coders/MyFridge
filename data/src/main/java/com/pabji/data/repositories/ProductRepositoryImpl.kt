package com.pabji.data.repositories

import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.ProductRemoteDatasource
import com.pabji.domain.Product

class ProductRepositoryImpl(
    private val localDataSource: LocalDatasource,
    private val remoteDataSource: ProductRemoteDatasource
) : ProductRepository {

    override fun getProducts() = localDataSource.getProductList()

    override fun saveProduct(product: Product) = localDataSource.saveProduct(product)

    override fun removeProduct(product: Product) = localDataSource.removeProduct(product)

    override fun searchProducts(searchTerm: String?, page: Int) =
        remoteDataSource.searchProducts(searchTerm, page)

    override fun getProductListByBarcodeList(barcodeList: List<String>) =
        localDataSource.getProductListByBarcodeList(barcodeList)

    override fun getProductById(productId: Long) = localDataSource.getProductById(productId)

    override fun getProductByBarcode(barcode: String) = localDataSource.getProductByBarcode(barcode)
        ?: remoteDataSource.getProductByBarcode(barcode)
}