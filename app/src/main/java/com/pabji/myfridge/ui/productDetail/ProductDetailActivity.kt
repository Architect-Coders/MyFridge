package com.pabji.myfridge.ui.productDetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import coil.size.Scale
import com.pabji.data.repositories.ProductRepositoryImpl
import com.pabji.myfridge.MyApp.Companion.app
import com.pabji.myfridge.R
import com.pabji.myfridge.common.extensions.getViewModel
import com.pabji.myfridge.common.extensions.gone
import com.pabji.myfridge.common.extensions.setVisible
import com.pabji.myfridge.common.extensions.visible
import com.pabji.myfridge.model.database.RoomDataSource
import com.pabji.myfridge.ui.common.uiModels.ItemProductList
import com.pabji.usecases.GetProductDetail
import com.pabji.usecases.SaveProduct
import kotlinx.android.synthetic.main.activity_product_detail.*

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        setTitle(R.string.product_detail_title)

        viewModel = getViewModel {
            val repository = ProductRepositoryImpl(RoomDataSource(app.db),)
            ProductDetailViewModel(
                intent.getSerializableExtra(INTENT_PRODUCT) as? ItemProductList,
                GetProductDetail(repository),
                SaveProduct(repository)
            )
        }.also {
            it.viewState.observe(this, Observer(::updateUI))
        }

        btn_add.setOnClickListener { viewModel.onClickButtonAdd() }
    }

    private fun updateUI(viewState: ProductDetailViewState?) {
        when (viewState) {
            is ShowProduct -> showProduct(viewState.product)
            is ShowSaved -> showSaved(viewState.product)
            ShowError -> showError()
        }
    }

    private fun showSaved(product: ProductDetail) {
        Toast.makeText(this, "${product.name} has been saved in your fridge", Toast.LENGTH_SHORT)
            .show()
        btn_add.gone()
    }

    private fun showProduct(
        product: ProductDetail
    ) {
        btn_add.setVisible(!product.existInFridge)
        product.run {
            iv_product_image.load(imageUrl) {
                scale(Scale.FILL)
                crossfade(true)
                error(R.mipmap.ic_launcher)
            }

            tv_product_name.text = name

            genericName.let {
                tv_generic_name.run {
                    text = it
                    visible()
                }
            }

            ingredientsText.let {
                tv_ingredients.text = it
                ll_ingredients.visible()
            }

            categories.let {
                if (it.isNotEmpty()) {
                    tv_categories.text = it.joinToString(", ")
                    ll_categories.visible()
                }
            }

            stores.let {
                if (it.isNotEmpty()) {
                    tv_stores.text = it.joinToString(", ")
                    ll_stores.visible()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val INTENT_PRODUCT = "intent_product"
    }

}
