package com.villageapp.ui.meal.savedcontent

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.models.meal.consume.list.DataMealFacts
import com.villageapp.models.meal.consume.list.MealFact
import com.villageapp.models.product.detail.ProductIngredient
import com.villageapp.ui.productdetail.ingredients.AdapterProductDetailIngredientCallback
import kotlinx.android.synthetic.main.adp_view_ingredients.view.*
import kotlinx.android.synthetic.main.adp_view_things_u_saved.view.*


class AdapterSavedContentOnMealConsume() : ListAdapter<MealFact, AdapterSavedContentOnMealConsume.ViewHolder>(
    AdapterSavedContentOnMealConsumeCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_things_u_saved, parent, false)



        return ViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(ingredient: MealFact?) {
            itemView.tv_content_u_saved?.text = ingredient?.fact


        }


    }


}