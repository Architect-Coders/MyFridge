package com.pabji.myfridge.ui.productList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.Event
import com.pabji.myfridge.ui.productList.ProductListViewModel.UiModel
import com.pabji.testshared.mockedLocalProductList
import com.pabji.usecases.GetMyProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getMyProducts: GetMyProducts

    @Mock
    lateinit var uiModelObserver: Observer<UiModel>

    @Mock
    lateinit var navigationObserver: Observer<Event<ItemProduct>>

    private lateinit var vm: ProductListViewModel

    @Before
    fun setUp() {
        vm = ProductListViewModel(getMyProducts, Dispatchers.Unconfined)
    }

    @Test
    fun `while observing Model LiveData, product list is shown`() {
        runBlocking {
            val itemProducts = mockedLocalProductList.map { it.toItemProduct() }
            whenever(getMyProducts.invoke()).thenReturn(mockedLocalProductList)
            vm.model.observeForever(uiModelObserver)
            vm.updateData()
            verify(uiModelObserver).onChanged(UiModel.Content(itemProducts))
        }
    }

    @Test
    fun `while observing Model LiveData, emptyList is shown`() {
        runBlocking {
            whenever(getMyProducts.invoke()).thenReturn(emptyList())
            vm.model.observeForever(uiModelObserver)
            vm.updateData()
            verify(uiModelObserver).onChanged(UiModel.EmptyList)
        }
    }

    @Test
    fun `while observing Navigation LiveData, event is launched`() {
        runBlocking {
            val product = ItemProduct()
            vm.navigation.observeForever(navigationObserver)
            vm.onProductClicked(product)
            verify(navigationObserver).onChanged(Event(product))
        }
    }
}
