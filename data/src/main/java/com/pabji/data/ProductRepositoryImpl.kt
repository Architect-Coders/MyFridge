package com.pabji.data

import com.pabji.domain.Product

class ProductRepositoryImpl(
    private val localDataSource: ProductLocalDatasource,
    private val remoteDataSource: ProductRemoteDatasource
) : ProductRepository {

    override fun getProducts() = localDataSource.getProductList()

    override fun saveProduct(product: Product) = localDataSource.saveProduct(product)

    override fun removeProduct(product: Product) = localDataSource.removeProduct(product)

    override fun searchProducts(searchTerm: String?, page: Int) =
        remoteDataSource.searchProducts(searchTerm, page)

    override fun getProductListByBarcodeList(barcodeList: List<String>) =
        localDataSource.getProductListByBarcodeList(barcodeList)

    override fun getProduct(productId: Long?, barcode: String?) = productId?.run {
        localDataSource.getProductById(this)
    } ?: run {
        barcode?.run {
            localDataSource.getProductByBarcode(this) ?: remoteDataSource.getProductByBarcode(this)
        }
    }
}

interface ProductLocalDatasource {
    fun getProductListByBarcodeList(barcodeList: List<String>): List<Product>
    fun getProductByBarcode(barcode: String): Product?
    fun getProductById(productId: Long): Product?
    fun getProductList(): List<Product>
    fun saveProduct(product: Product)
    fun removeProduct(product: Product)
}

interface ProductRemoteDatasource {
    fun searchProducts(searchTerm: String?, page: Int): List<Product>
    fun getProductByBarcode(barcode: String): Product?
}