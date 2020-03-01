package com.pabji.usecases

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.repositories.ProductRepository
import com.pabji.domain.DetailError
import com.pabji.domain.Either
import com.pabji.testshared.mockedProduct
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetProductDetailTest {

    @Mock
    lateinit var productRepository: ProductRepository

    lateinit var getProductDetail: GetProductDetail

    @Before
    fun setUp() {
        getProductDetail = GetProductDetail(productRepository)
    }

    @Test
    fun `when invoke should return product`() {
        runBlocking {
            val product = mockedProduct.copy(id = 1)
            whenever(productRepository.getProductDetail(product)).thenReturn(Either.Right(product))

            val result = getProductDetail(product)

            verify(productRepository).getProductDetail(product)
            assertTrue(result.isRight)
            assertEquals(Either.Right(product), result)
        }
    }

    @Test
    fun `when invoke should return error`() {
        runBlocking {
            val product = mockedProduct.copy()
            whenever(productRepository.getProductDetail(any())).thenReturn(Either.Left(DetailError))

            val result = getProductDetail(product)

            verify(productRepository).getProductDetail(any())

            assertTrue(result.isLeft)
        }
    }
}
