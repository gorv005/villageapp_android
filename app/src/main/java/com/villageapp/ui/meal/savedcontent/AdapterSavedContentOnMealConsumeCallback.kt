package com.villageapp.ui.meal.savedcontent

import android.support.v7.util.DiffUtil
import com.villageapp.models.meal.consume.list.DataMealFacts
import com.villageapp.models.meal.consume.list.MealFact

class AdapterSavedContentOnMealConsumeCallback : DiffUtil.ItemCallback<MealFact>() {

    override fun areItemsTheSame(oldItem: MealFact, newItem: MealFact) = oldItem.id == newItem.id

    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: MealFact, newItem: MealFact): Boolean {
        return oldItem == newItem
    }
}