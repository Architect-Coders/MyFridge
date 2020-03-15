package com.pabji.myfridge.ui.productDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.api.load
import coil.size.Scale
import com.pabji.domain.Product
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.ui.common.extensions.gone
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

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewModel.model.observe(this, Observer(::updateUI))
        btn_add.setOnClickListener { viewModel.onClickButton() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun updateUI(viewState: UiModel?) {
        when (viewState) {
            is UiModel.BasicContent -> showBasicProduct(viewState.product)
            is UiModel.FullContent -> showProduct(viewState.product)
            is UiModel.ProductSaved -> showSaved(viewState.product)
            is UiModel.ProductRemoved -> showRemoved(viewState.product)
            UiModel.Error -> showError()
        }
    }

    private fun showBasicProduct(product: ItemProduct?) {
        product?.run {
            title = name
            setButton(existInFridge)
            setProductImage(previewUrl)
            setProductName(name)
        }
    }

    private fun showRemoved(product: Product) {
        Toast.makeText(
                this,
                "${product.name} has been removed from your fridge",
                Toast.LENGTH_SHORT
            )
            .show()
        setButton(false)
    }

    private fun showSaved(product: Product) {
        Toast.makeText(this, "${product.name} has been saved in your fridge", Toast.LENGTH_SHORT)
            .show()
        setButton(true)
    }

    private fun showProduct(product: Product) {
        product.run {
            setButton(existInFridge)
            setProductImage(imageUrl)
            setProductName(name)
            setGenericName(genericName)
            setIngredientsText(ingredientsText)
            setCategories(categories)
            setStores(stores)
        }
    }

    private fun setButton(existInFridge: Boolean) {
        if (existInFridge) {
            btn_add.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent))
            btn_add.text = getString(R.string.remove_from_fridge)
        } else {
            btn_add.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            btn_add.text = getString(R.string.add_to_the_fridge)
        }
        btn_add.visible()
    }

    private fun setStores(stores: String) {
        if (stores.isNotEmpty()) {
            tv_stores.text = stores
            ll_stores.visible()
        } else {
            ll_stores.gone()
        }
    }

    private fun setCategories(categories: String) {
        if (categories.isNotEmpty()) {
            tv_categories.text = categories
            ll_categories.visible()
        } else {
            ll_categories.gone()
        }
    }

    private fun setIngredientsText(ingredientsText: String) {
        if (ingredientsText.isNotEmpty()) {
            tv_ingredients.text = ingredientsText
            ll_ingredients.visible()
        } else {
            ll_ingredients.gone()
        }
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
