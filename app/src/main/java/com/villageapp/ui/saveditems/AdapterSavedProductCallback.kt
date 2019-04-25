package com.villageapp.ui.saveditems

import android.support.v7.util.DiffUtil
import com.villageapp.models.user.favouritelist.UserSavedFavourites


class AdapterSavedProductCallback : DiffUtil.ItemCallback<UserSavedFavourites>() {
    override fun areItemsTheSame(old: UserSavedFavourites, new: UserSavedFavourites): Boolean {

        return old.areSame(new)

    }


    /* This method is called only if {@link #areItemsTheSame(T, T)} returns {@code true} for these items */
    override fun areContentsTheSame(oldItem: UserSavedFavourites, newItem: UserSavedFavourites): Boolean {
        return oldItem == newItem
    }
}