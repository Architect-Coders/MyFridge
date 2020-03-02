package com.pabji.myfridge.ui.productDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import coil.size.Scale
import com.pabji.domain.Product
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.ui.common.extensions.gone
import com.pabji.myfridge.ui.common.extensions.setVisible
import com.pabji.myfridge.ui.common.extensions.visible
import com.pabji.myfridge.ui.productDetail.ProductDetailViewModel.UiModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : AppCompatActivity() {

    private val viewModel: ProductDetailViewModel by currentScope.viewModel(this) {
        parametersOf(intent.getSerializableExtra(INTENT_PRODUCT) as? ItemProduct)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setTitle(R.string.product_detail_title)

        viewModel.model.observe(this, Observer(::updateUI))
        btn_add.setOnClickListener { viewModel.onClickButtonAdd() }
    }

    private fun updateUI(viewState: UiModel?) {
        when (viewState) {
            UiModel.Loading -> {
            }
            is UiModel.Content -> showProduct(viewState.product)
            is UiModel.ProductSaved -> showSaved(viewState.product)
            UiModel.Error -> showError()
        }
    }

    private fun showSaved(product: Product) {
        Toast.makeText(this, "${product.name} has been saved in your fridge", Toast.LENGTH_SHORT)
            .show()
        btn_add.gone()
    }

    private fun showProduct(product: Product) {
        product.run {
            btn_add.setVisible(!existInFridge)
            setProductImage(imageUrl)
            setProductName(name)
            setGenericName(genericName)
            setIngredientsText(ingredientsText)
            setCategories(categories)
            setStores(stores)
        }
    }

    private fun setStores(stores: String) {
        if (stores.isNotEmpty()) {
            tv_stores.text = stores
            ll_stores.visible()
        }
    }

    private fun setCategories(categories: String) {
        if (categories.isNotEmpty()) {
            tv_categories.text = categories
            ll_categories.visible()
        }
    }

    private fun setIngredientsText(ingredientsText: String) {
        tv_ingredients.text = ingredientsText
        ll_ingredients.visible()
    }

    private fun setGenericName(genericName: String) {
        tv_generic_name.run {
            text = genericName
            visible()
        }
    }

    private fun setProductName(name: String) {
        tv_product_name.text = name
    }

    private fun setProductImage(imageUrl: String) {
        iv_product_image.load(imageUrl) {
            scale(Scale.FILL)
            crossfade(true)
            error(R.mipmap.ic_launcher)
        }
    }

    private fun showError() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val INTENT_PRODUCT = "intent_product"
    }

}
