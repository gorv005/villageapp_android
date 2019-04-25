package com.villageapp.ui.productdetail.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.villageapp.R
import com.villageapp.ui.productdetail.directions.FragmentProductDirections
import com.villageapp.ui.productdetail.ingredients.FragmentProductIngredients
import com.villageapp.ui.productdetail.summary.FragmentProductSummary
import com.villageapp.utils.AndroidUtils

class ProductDetailViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> FragmentProductSummary.getInstance()
            1 -> FragmentProductDirections.getInstance()
            else -> FragmentProductIngredients.getInstance()
        }
    }

    override fun getCount() =
            AndroidUtils.getStringArray(R.array.product_detail_tabs_title)?.size ?: 0

    override fun getPageTitle(position: Int): CharSequence? {
        return AndroidUtils.getStringArray(R.array.product_detail_tabs_title)?.get(position) ?: ""
    }
}