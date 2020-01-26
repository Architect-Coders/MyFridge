package com.pabji.data.repositories

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.data.datasources.LocalDatasource
import com.pabji.data.datasources.RemoteDatasource
import com.pabji.domain.DetailError
import com.pabji.domain.Either
import com.pabji.testshared.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDatasource

    @Mock
    lateinit var remoteDatasource: RemoteDatasource

    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        productRepository = ProductRepositoryImpl(localDataSource, remoteDatasource)
    }

    @Test
    fun `when getProducts should return product list`() {
        runBlocking {
            val productList = listOf(mockedProduct.copy())
            whenever(localDataSource.getProductList()).thenReturn(productList)

            val result = productRepository.getProducts()

            assertEquals(productList, result)
        }
    }

    @Test
    fun `when saveProduct should save in localDatasource`() {
        runBlocking {
            val product = mockedProduct.copy()

            productRepository.saveProduct(product)

            verify(localDataSource).saveProduct(product)
        }
    }

    @Test
    fun `when removeProduct should remove in localDatasource`() {
        runBlocking {
            val product = mockedProduct.copy()

            productRepository.removeProduct(product)

            verify(localDataSource).removeProduct(product)
        }
    }

    @Test
    fun `when searchProduct with searchTerm should return productList`() {
        runBlocking {
            val searchTerm = "Product"
            val localProductList = mockedLocalProductList
            val remoteProductList = mockedRemoteProductList

            whenever(localDataSource.getProductsByTerm(searchTerm)).thenReturn(localProductList)
            whenever(remoteDatasource.searchProducts(searchTerm)).thenReturn(
                Either.Right(
                    remoteProductList
                )
            )

            val result = productRepository.searchProducts(searchTerm)

            verify(localDataSource).getProductsByTerm(searchTerm)
            verify(remoteDatasource).searchProducts(searchTerm)

            assertEquals(mockedFilteredProducts, result)
        }
    }

    @Test
    fun `when searchProduct without should return productList`() {
        runBlocking {
            val remoteProductList = mockedRemoteProductList

            whenever(localDataSource.getProductsByTerm("")).thenReturn(emptyList())
            whenever(remoteDatasource.searchProducts(null)).thenReturn(
                Either.Right(
                    remoteProductList
                )
            )

            val result = productRepository.searchProducts()

            verify(localDataSource).getProductsByTerm("")
            verify(remoteDatasource).searchProducts(null)

            assertEquals(remoteProductList, result)
        }
    }

    @Test
    fun `when getProductById with id should return product`() {
        runBlocking {
            val product = mockedProduct.copy(id = 1)

            whenever(localDataSource.getProductById(1)).thenReturn(Either.Right(product))

            val result = productRepository.getProductById(1)

            verify(localDataSource).getProductById(1)
            assertTrue(result.isRight)
            assertEquals(Either.Right(product), result)
        }
    }

    @Test
    fun `when getProductById should return error`() {
        runBlocking {

            whenever(localDataSource.getProductById(any())).thenReturn(Either.Left(DetailError))

            val result = productRepository.getProductById(any())

            verify(localDataSource).getProductById(any())
            assertTrue(result.isLeft)
        }
    }

    @Test
    fun `when getProductByBarcode with barcode should return product from localDatasource`() {
        runBlocking {
            val mockedBarcode = "123456"
            val product = mockedLocalProduct.copy(barcode = mockedBarcode)

            whenever(localDataSource.getProductByBarcode(mockedBarcode)).thenReturn(
                Either.Right(
                    product
                )
            )

            val result = productRepository.getProductByBarcode(mockedBarcode)

            verify(localDataSource).getProductByBarcode(mockedBarcode)
            assertTrue(result.isRight)
            assertEquals(Either.Right(product), result)
        }
    }

    @Test
    fun `when getProductByBarcode with barcode should return product from remoteDatasource`() {
        runBlocking {
            val mockedBarcode = "123456"
            val product = mockedLocalProduct.copy(barcode = mockedBarcode)

            whenever(localDataSource.getProductByBarcode(any())).thenReturn(Either.Left(DetailError))
            whenever(remoteDatasource.getProductByBarcode(mockedBarcode)).thenReturn(
                Either.Right(
                    product
                )
            )

            val result = productRepository.getProductByBarcode(mockedBarcode)

            verify(localDataSource).getProductByBarcode(any())
            verify(remoteDatasource).getProductByBarcode(mockedBarcode)

            assertTrue(result.isRight)
            assertEquals(Either.Right(product), result)
        }
    }

    @Test
    fun `when getProductByBarcode with barcode should return error`() {
        runBlocking {
            val mockedBarcode = "123456"

            whenever(localDataSource.getProductByBarcode(any())).thenReturn(Either.Left(DetailError))
            whenever(remoteDatasource.getProductByBarcode(any())).thenReturn(Either.Left(DetailError))

            val result = productRepository.getProductByBarcode(mockedBarcode)

            verify(localDataSource).getProductByBarcode(any())
            verify(remoteDatasource).getProductByBarcode(any())

            assertTrue(result.isLeft)
        }
    }

    @Test
    fun `when getFilteredProductsByProducts in productList should return filtered list by barcode`() {
        val localProductList = mockedLocalProductList
        val remoteProductList = mockedRemoteProductList

        val result = remoteProductList.getFilteredProductsByProducts(localProductList)

        assertEquals(mockedFilteredProducts, localProductList + result)
    }
}
