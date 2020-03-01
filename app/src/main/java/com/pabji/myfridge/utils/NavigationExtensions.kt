package com.pabji.myfridge.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.ui.common.extensions.startActivity
import com.pabji.myfridge.ui.productDetail.ProductDetailActivity

fun Activity.goToProductDetail(product: ItemProduct) {
    startActivity<ProductDetailActivity> {
        putExtra(ProductDetailActivity.INTENT_PRODUCT, product)
    }
}

fun Fragment.goToProductDetail(product: ItemProduct) {
    startActivity<ProductDetailActivity> {
        putExtra(ProductDetailActivity.INTENT_PRODUCT, product)
    }
}



