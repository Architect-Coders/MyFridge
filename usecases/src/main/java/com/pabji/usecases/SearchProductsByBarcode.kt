package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class SearchProductsByBarcode(private val productRepository: ProductRepository) {

    private val mutableProductList = mutableSetOf<Product>()

    @Synchronized
    suspend operator fun invoke(
        productList: Set<Product>,
        newBarcodeList: Set<String>
    ): Set<Product> {
        val productListBarcode = productList.map { it.barcode }
        val searchList =
            newBarcodeList.filter { it.isNotEmpty() }.subtract(productListBarcode).toList()
        mutableProductList.addAll(productRepository.searchProductsByBarcode(searchList))
        return mutableProductList
    }
}