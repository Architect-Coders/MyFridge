package com.pabji.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.repositories.ProductRepository
import com.pabji.testshared.mockedBarcodeList
import com.pabji.testshared.mockedRemoteProductList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchProductByBarcodeTest {

    @Mock
    lateinit var productRepository: ProductRepository

    lateinit var searchProductsByBarcode: SearchProductsByBarcode

    @Before
    fun setUp() {
        searchProductsByBarcode = SearchProductsByBarcode(productRepository)
    }

    @Test
    fun `when invoke should return remote productsSet`() {
        runBlocking {
            whenever(productRepository.searchProductsByBarcode(any())).thenReturn(
                mockedRemoteProductList
            )

            val result = searchProductsByBarcode(emptySet(), mockedBarcodeList.toSet())

            verify(productRepository).searchProductsByBarcode(any())
            Assert.assertEquals(mockedRemoteProductList.toSet(), result)
        }
    }
}
