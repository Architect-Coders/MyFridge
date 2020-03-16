package com.pabji.myfridge.ui.common.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.pabji.myfridge.R
import com.pabji.myfridge.model.ItemProduct
import com.pabji.myfridge.ui.common.extensions.notifyChanges
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlin.properties.Delegates

class ProductListAdapter(
    private val recyclerType: RecyclerType = RecyclerType.LIST,
    private val onProductClicked: (ItemProduct) -> Unit
) :
    RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    enum class RecyclerType { GRID, LIST }

    var productList: List<ItemProduct> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList) { o, n -> o.name == n.name }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            when (recyclerType) {
                RecyclerType.GRID -> R.layout.item_product_grid
                RecyclerType.LIST -> R.layout.item_product_list
            }, parent, false
        )

        return ProductViewHolder(
            view,
            onProductClicked
        )
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    class ProductViewHolder(view: View, private val onProductClicked: (ItemProduct) -> Unit) :
        RecyclerView.ViewHolder(view) {

        fun bind(product: ItemProduct) {
            itemView.run {
                tv_product_name.text = product.name
                iv_product_picture.load(product.previewUrl) {
                    crossfade(true)
                    error(R.mipmap.ic_launcher)
                }
                card_view.setCardBackgroundColor(
                    if (product.existInFridge) {
                        ContextCompat.getColor(context, R.color.lightgreen)
                    } else {
                        ContextCompat.getColor(context, R.color.white)
                    }
                )
                setOnClickListener { onProductClicked(product) }
            }
        }
    }
}

