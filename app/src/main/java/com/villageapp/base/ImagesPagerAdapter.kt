package com.villageapp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ImagesPagerAdapter(fragmentManager: FragmentManager, val images: List<String>) :
    FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return FragmentImage.getInstance(images[position])
    }

    override fun getCount(): Int = images.size


}