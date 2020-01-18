package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository

class SearchProductByTerm(private val productRepository: ProductRepository) {

    operator fun invoke(searchTerm: String, page: Int = 1) = with(productRepository) {
        val searchProducts = searchProducts(searchTerm, page)
        val dbProducts =
            getProductListByBarcodeList(searchProducts.map { it.barcode }).map { it.barcode }

        searchProducts.map {
            if (it.barcode in dbProducts) {
                it.existInFridge = true
            }
            it
        }
    }
}