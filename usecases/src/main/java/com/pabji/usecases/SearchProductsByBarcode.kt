package com.pabji.usecases

import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.Product

class SearchProductsByBarcode(private val productRepository: ProductRepository) {

    suspend operator fun invoke(barcodeList: List<String>): List<Product> =
        productRepository.searchProductsByBarcode(barcodeList)

}