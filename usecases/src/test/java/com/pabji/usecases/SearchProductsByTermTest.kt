package com.pabji.usecases

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.repositories.ProductRepository
import com.pabji.testshared.mockedProduct
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchProductsByTermTest {

    @Mock
    lateinit var productRepository: ProductRepository

    lateinit var searchProductsByTerm: SearchProductsByTerm

    @Before
    fun setUp() {
        searchProductsByTerm = SearchProductsByTerm(productRepository)
    }

    @Test
    fun `when invoke should return product list by searchTerm`() {
        runBlocking {
            val searchTerm = "Product"
            val productList = listOf(mockedProduct.copy())
            whenever(productRepository.searchProducts(searchTerm)).thenReturn(productList)

            val result = searchProductsByTerm(searchTerm)

            verify(productRepository).searchProducts(searchTerm)
            Assert.assertEquals(productList, result)
        }
    }
}
