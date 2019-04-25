package com.villageapp.ui.productdetail.ingredients

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.models.product.detail.ProductIngredient
import kotlinx.android.synthetic.main.adp_view_ingredients.view.*


class AdapterProductDetailIngredients() : ListAdapter<ProductIngredient, AdapterProductDetailIngredients.ViewHolder>(
    AdapterProductDetailIngredientCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_ingredients, parent, false)



        return ViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(ingredient: ProductIngredient) {
            itemView.tv_ingredient_name?.text = ingredient.name
            itemView.tv_quantity?.text = ingredient.quantity
        }


    }


}