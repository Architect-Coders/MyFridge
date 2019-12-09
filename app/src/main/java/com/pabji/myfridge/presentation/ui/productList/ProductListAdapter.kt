package com.pabji.myfridge.presentation.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pabji.myfridge.R
import com.pabji.myfridge.common.notifyChanges
import com.pabji.myfridge.presentation.models.Product
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlin.properties.Delegates


class ProductListAdapter(private val onProductClicked: (Product) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    var productList: List<Product> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList) { o, n -> o.name == n.name }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductViewHolder(v, onProductClicked)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    class ProductViewHolder(view: View, private val onProductClicked: (Product) -> Unit) :
        RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            itemView.run {
                tv_product_name.text = product.name
                setOnClickListener { onProductClicked(product) }
            }
        }
    }
}