package com.villageapp.ui.home.adapter.popularrecipies

import android.app.Activity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.home.Category
import com.villageapp.ui.catageoryprodlist.ActivityProductList
import com.villageapp.utils.AndroidUtils
import kotlinx.android.synthetic.main.adp_view_popular_recipies.view.*


class AdapterPopulerRecipes(
    private val clickListener: (Category, Int) -> Unit,
    val activity: Activity
) : ListAdapter<Category, AdapterPopulerRecipes.ViewHolder>(
    AdapterPopulerRecipesCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_popular_recipies, parent, false)

        val displayMetrics = DisplayMetrics()
        activity.windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        itemView.setLayoutParams(RecyclerView.LayoutParams(width - (width / 5), RecyclerView.LayoutParams.WRAP_CONTENT))


        return ViewHolder(
            itemView, activity
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder(itemView: View, val activity: Activity) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(category: Category, clickListener: (Category, Int) -> Unit) {
            itemView.tv_recipe_catgeory_name?.text = category.name
            itemView.tv_recipe_total_count?.text = category.product_count?.toString() + " Recipes"

            ImageRequestManager.with(itemView.sv_recipes_image)
                .url(category.image)
                .roundedCornerRadius(imageCornerRadius)
                .build()

            itemView.setOnClickListener {

                val intent = category.id?.let { ActivityProductList.getIntent(activity, it,category.name) }
                intent?.let { activity.startActivity(it) }
            }
        }


    }



}