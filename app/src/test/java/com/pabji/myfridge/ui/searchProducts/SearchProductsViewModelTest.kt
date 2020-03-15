package com.pabji.myfridge.ui.searchProducts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.model.toItemProduct
import com.pabji.myfridge.ui.common.Event
import com.pabji.myfridge.ui.searchProducts.SearchProductsViewModel.UiModel
import com.pabji.testshared.mockedRemoteProductList
import com.pabji.usecases.SearchProductsByTerm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchProductsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchProductsByTerm: SearchProductsByTerm

    @Mock
    lateinit var uiModelObserver: Observer<UiModel>

    @Mock
    lateinit var navigationObserver: Observer<Event<ItemProduct>>

    private lateinit var vm: SearchProductsViewModel

    @Before
    fun setUp() {
        vm = SearchProductsViewModel(searchProductsByTerm, Dispatchers.Unconfined)
    }

    @Test
    fun `while observing Model LiveData, empty is shown`() {
        runBlocking {
            whenever(searchProductsByTerm.invoke(any())).thenReturn(emptyList())
            vm.model.observeForever(uiModelObserver)
            vm.onSearch("")
            verify(uiModelObserver).onChanged(UiModel.EmptyList)
        }
    }

    @Test
    fun `while observing Model LiveData, random product list is shown`() {
        runBlocking {
            val itemProducts = mockedRemoteProductList.map { it.toItemProduct() }
            whenever(searchProductsByTerm.invoke(any())).thenReturn(mockedRemoteProductList)
            vm.model.observeForever(uiModelObserver)
            vm.onSearch("")
            verify(uiModelObserver).onChanged(UiModel.Content(itemProducts))
        }
    }

    @Test
    fun `while observing Model LiveData and search with term, product list is shown`() {
        runBlocking {
            val term = "product"
            val itemProducts = mockedRemoteProductList.map { it.toItemProduct() }
            whenever(searchProductsByTerm.invoke(term)).thenReturn(mockedRemoteProductList)
            vm.onSearch(term)
            vm.model.observeForever(uiModelObserver)
            verify(uiModelObserver).onChanged(UiModel.Content(itemProducts))
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
