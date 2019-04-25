package com.villageapp.ui.search

import android.support.v7.util.DiffUtil
import com.villageapp.models.home.search.Result


class AdapterHomeSearchCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(p0: Result, p1: Result): Boolean {
        return p0 == p1
    }


    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}