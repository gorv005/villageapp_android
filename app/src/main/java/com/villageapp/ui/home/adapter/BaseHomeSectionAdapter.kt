package com.villageapp.ui.home.adapter

import android.app.Activity
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

abstract class BaseHomeSectionAdapter<E : Any?, VH : RecyclerView.ViewHolder?>(
    val context: Activity,
    diffCallback: DiffUtil.ItemCallback<E>
) : ListAdapter<E, VH>(diffCallback) {
}