package com.pabji.myfridge.ui.productDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.domain.DetailError
import com.pabji.domain.Either
import com.pabji.myfridge.model.toItemProduct
import com.pabji.testshared.mockedProduct
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getProductDetail: GetProductDetail

    @Mock
    lateinit var saveProduct: SaveProduct

    @Mock
    lateinit var uiModelObserver: Observer<ProductDetailViewModel.UiModel>

    private lateinit var vm: ProductDetailViewModel

    private val mockedItemProduct = mockedProduct.toItemProduct()

    @Before
    fun setUp() {
        vm = ProductDetailViewModel(
            mockedItemProduct,
            getProductDetail,
            saveProduct,
            Dispatchers.Unconfined
        )
    }

    @Test
    fun `while observing Model LiveData, product is shown`() {
        runBlocking {
            whenever(getProductDetail(any())).thenReturn(Either.Right(mockedProduct))
            vm.model.observeForever(uiModelObserver)
            verify(uiModelObserver).onChanged(ProductDetailViewModel.UiModel.Content(mockedProduct))
        }
    }

    @Test
    fun `while observing Model LiveData, error is shown`() {
        runBlocking {
            whenever(getProductDetail(any())).thenReturn(Either.Left(DetailError))
            vm.model.observeForever(uiModelObserver)
            verify(uiModelObserver).onChanged(ProductDetailViewModel.UiModel.Error)
        }
    }

    @Test
    fun `while observing Model LiveData and onClickButtonAdd, save product is shown`() {
        runBlocking {
            whenever(getProductDetail(any())).thenReturn(Either.Right(mockedProduct))
            vm.model.observeForever(uiModelObserver)
            vm.onClickButtonAdd()
            verify(uiModelObserver).onChanged(
                ProductDetailViewModel.UiModel.ProductSaved(
                    mockedProduct
                )
            )
        }
    }

    @Test
    fun `while observing Model LiveData snf Itemproduct is null, error is shown`() {
        val vm = ProductDetailViewModel(null, getProductDetail, saveProduct, Dispatchers.Unconfined)
        runBlocking {
            vm.model.observeForever(uiModelObserver)
            verify(uiModelObserver).onChanged(ProductDetailViewModel.UiModel.Error)
        }
    }
}
