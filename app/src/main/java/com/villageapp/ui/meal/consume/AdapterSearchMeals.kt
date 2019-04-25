package com.villageapp.ui.meal.consume

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.villageapp.R
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.meal.search.Meal
import com.villageapp.utils.AndroidUtils
import kotlinx.android.synthetic.main.adp_view_meals_and_points.view.*

class AdapterSearchMeals() : ListAdapter<Meal, AdapterSearchMeals.ViewHolder>(
    AdapterSearchMealCallback()
) {

    var selectedMeals = ArrayList<Meal>()
    var totalPointsToEarned: Int = 0;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.adp_view_meals_and_points, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun getSelectedMealIds(): String? {


        var ids: String? = null;
        totalPointsToEarned = 0

        for (item in selectedMeals) {
            if (item.id != null && item.isSelected) {

                val idStr = item.id.toString()
                if (ids == null)
                    ids = idStr
                else
                    ids = "$ids,$idStr"

                item.points?.let {
                    totalPointsToEarned += it
                }

            }
        }

        return ids

    }


    fun getPointsEarned(): Int {
        return totalPointsToEarned
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_dp_10).toFloat()

        fun bind(meal: Meal) {

            itemView.tv_meal_and_points_desc?.text = meal.description
            itemView.tv_meal_and_points_name?.text = meal.name
            itemView.tv_meal_and_points?.text = AndroidUtils.getString(R.string.points_for_search_meal, meal.points)
            ImageRequestManager.with(itemView.sv_meal_and_points)
                .url(meal.image)
                .roundedCornerRadius(imageCornerRadius)
                .build()

            switchViewIfMealSelected(meal)



            itemView.setOnClickListener {

                if (meal.isSelected) {
                    selectedMeals.remove(meal)
                } else {
                    selectedMeals.add(meal)

                }

                meal.isSelected = !meal.isSelected
                switchViewIfMealSelected(meal)
            }


        }

        private fun switchViewIfMealSelected(meal: Meal) {
            itemView.ll_meal_and_points_container?.setBackgroundColor(AndroidUtils.getColor(if (meal.isSelected) R.color.f8_f8_f8 else R.color.white))
            itemView.iv_meal_and_points_selected.visibility = if (meal.isSelected) View.VISIBLE else View.INVISIBLE

        }
    }


}