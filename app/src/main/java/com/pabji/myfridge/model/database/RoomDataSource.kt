package com.pabji.myfridge.model.database

import com.pabji.data.datasources.LocalDatasource
import com.pabji.domain.Product
import com.pabji.myfridge.model.database.entities.toProduct
import com.pabji.myfridge.model.database.entities.toProductEntity

class RoomDataSource(database: ProductDatabase) : LocalDatasource {

    private val productDao = database.productDao()

    override fun getProductListByBarcodeList(barcodeList: List<String>): List<Product> =
        productDao.getProductsByBarcode(barcodeList).map { it.toProduct() }

    override fun getProductByBarcode(barcode: String): Product? =
        productDao.getProductByBarcode(barcode)?.toProduct()

    override fun getProductById(productId: Long): Product? =
        productDao.getProductById(productId)?.toProduct()

    override fun getProductList(): List<Product> = productDao.getAll().map { it.toProduct() }

    override fun saveProduct(product: Product) = productDao.insert(product.toProductEntity())

    override fun removeProduct(product: Product) = productDao.remove(product.toProductEntity())
}