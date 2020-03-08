package com.pabji.testshared

import com.pabji.domain.Product

val mockedBarcodeList = listOf("1234567", "7654321")

val mockedProduct = Product(barcode = "1234567", name = "ProductName")

val mockedLocalProduct = mockedProduct.copy(existInFridge = true)

val mockedLocalProductList = listOf(
    mockedLocalProduct.copy(id = 1)
)

val mockedRemoteProductList = listOf(
    mockedProduct.copy(),
    mockedProduct.copy(name = "Product2", barcode = "7654321")
)

val mockedFilteredProducts = listOf(
    mockedLocalProduct.copy(1),
    mockedProduct.copy(name = "Product2", barcode = "7654321")
)
