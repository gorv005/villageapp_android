package com.villageapp.ui.onboarding

import android.os.Bundle
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.ui.home.HomeActivity
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : BaseFragment() {

    internal var images = intArrayOf(R.drawable.on_boarding_1, R.drawable.on_boarding_2, R.drawable.on_boarding_3)
    internal var descString = intArrayOf(R.string.on_boarding_1, R.string.on_boarding_2, R.string.on_boarding_3)


    override fun getLayoutId() = R.layout.fragment_onboarding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        arguments?.let {
            val currentPosition = arguments?.getInt(KEY_POSITION, 0)

            UiUtils.loadImage(images[currentPosition!!], iv_logo)

            tv_onboarding_desc.text = AndroidUtils.getString(descString[currentPosition])

            if (currentPosition == 2) {
                onboardingactivity_getstarted.visibility = View.VISIBLE
            } else {
                onboardingactivity_getstarted.visibility = View.GONE

            }
        }

        onboardingactivity_getstarted.setOnClickListener {
            activity?.let { it1 ->
                startActivity(HomeActivity.getIntent(it1))
                it1.finish()

            }

        }

    }


    companion object {


        val KEY_POSITION = "KEY_POSITION"

        fun getInstance(position: Int): OnboardingFragment {
            val bundle = Bundle()

            bundle.putInt(KEY_POSITION, position)
            val fragment = OnboardingFragment()

            fragment.arguments = bundle

            return fragment
        }
    }
}