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
    fun `when invoke should return product by id`() {
        runBlocking {
            val product = mockedProduct.copy(id = 1)
            whenever(productRepository.getProductById(1)).thenReturn(Either.Right(product))

            val result = getProductDetail(product)

            verify(productRepository).getProductById(1)
            assertTrue(result.isRight)
            assertEquals(Either.Right(product), result)
        }
    }

    @Test
    fun `when invoke should return product by barcode`() {
        runBlocking {
            val mockBarcode = "123456"
            val product = mockedProduct.copy(barcode = mockBarcode)
            whenever(productRepository.getProductById(any())).thenReturn(Either.Left(DetailError))
            whenever(productRepository.getProductByBarcode(mockBarcode)).thenReturn(
                Either.Right(
                    product
                )
            )

            val result = getProductDetail(product)

            verify(productRepository).getProductById(any())
            verify(productRepository).getProductByBarcode(mockBarcode)

            assertTrue(result.isRight)
            assertEquals(Either.Right(product), result)
        }
    }

    @Test
    fun `when invoke should return error`() {
        runBlocking {
            val product = mockedProduct.copy()
            whenever(productRepository.getProductById(any())).thenReturn(Either.Left(DetailError))
            whenever(productRepository.getProductByBarcode(any())).thenReturn(
                Either.Left(
                    DetailError
                )
            )

            val result = getProductDetail(product)

            verify(productRepository).getProductById(any())
            verify(productRepository).getProductByBarcode(any())

            assertTrue(result.isLeft)
        }
    }
}
