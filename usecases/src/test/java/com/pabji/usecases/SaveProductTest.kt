package com.pabji.usecases

import com.nhaarman.mockitokotlin2.verify
import com.pabji.data.repositories.ProductRepository
import com.pabji.testshared.mockedProduct
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SaveProductTest {

    @Mock
    lateinit var productRepository: ProductRepository

    lateinit var saveProduct: SaveProduct

    @Before
    fun setUp() {
        saveProduct = SaveProduct(productRepository)
    }

    @Test
    fun `when invoke should call saveProduct from repository`() {
        runBlocking {
            val product = mockedProduct.copy()
            saveProduct(product)
            verify(productRepository).saveProduct(product)
        }
    }
}
