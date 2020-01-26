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
class RemoveProductTest {

    @Mock
    lateinit var productRepository: ProductRepository

    lateinit var removeProduct: RemoveProduct

    @Before
    fun setUp() {
        removeProduct = RemoveProduct(productRepository)
    }

    @Test
    fun `when invoke should call removeProduct from repository`() {
        runBlocking {
            val product = mockedProduct.copy(id = 1)
            removeProduct(product)
            verify(productRepository).removeProduct(product)
        }
    }
}
