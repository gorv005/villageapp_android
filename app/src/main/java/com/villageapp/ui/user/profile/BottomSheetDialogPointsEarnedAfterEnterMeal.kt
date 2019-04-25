package com.villageapp.ui.user.profile

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.villageapp.R
import com.villageapp.base.BaseBottomSheetFragment
import com.villageapp.utils.AndroidUtils

class BottomSheetDialogPointsEarnedAfterEnterMeal : BaseBottomSheetFragment() {


    override fun getLayoutId() = R.layout.bs_change_earned_points_after_meal

    override fun init(rootView: View?, savedInstanceState: Bundle?) {


        setPointsEarned(rootView)


        rootView?.findViewById<TextView>(R.id.tv_dismiss)?.setOnClickListener {
            dismiss()

        }

        rootView?.findViewById<TextView>(R.id.tv_learned_what_u_saved)?.setOnClickListener {

            if (parentFragment is FragmentUserProfile) {
                (parentFragment as FragmentUserProfile).openSaveContentPage()
            }

            dismiss()

        }


    }

    private fun setPointsEarned(rootView: View?) {

        rootView?.findViewById<TextView>(R.id.tv_earned_points_on_meal_consumed)?.text =
                AndroidUtils.getString(R.string.you_have_earned_points_default)

        if (parentFragment is FragmentUserProfile) {
            (parentFragment as FragmentUserProfile).earnedOnThisSessionConsumption?.let {

                if (it > 0) {
                    rootView?.findViewById<TextView>(R.id.tv_earned_points_on_meal_consumed)?.text =
                            AndroidUtils.getString(R.string.you_have_earned_points,it)
                }
            }
        }
    }
}