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
class GetMyProductsTest {

    @Mock
    lateinit var productRepository: ProductRepository

    lateinit var getMyProducts: GetMyProducts

    @Before
    fun setUp() {
        getMyProducts = GetMyProducts(productRepository)
    }

    @Test
    fun `when invoke should return product list`() {
        runBlocking {
            val productList = listOf(mockedProduct.copy())
            whenever(productRepository.getProducts()).thenReturn(productList)

            val result = getMyProducts()

            verify(productRepository).getProducts()
            Assert.assertEquals(productList, result)
        }
    }
}
