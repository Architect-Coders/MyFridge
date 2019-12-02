package com.pabji.myfridge.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pabji.myfridge.R
import com.pabji.myfridge.common.notifyChanges
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlin.properties.Delegates


class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var productList: List<Product> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList) { o, n -> o.name == n.name }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductViewHolder(v)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            itemView.tv_product_name.text = product.name
        }
    }
}