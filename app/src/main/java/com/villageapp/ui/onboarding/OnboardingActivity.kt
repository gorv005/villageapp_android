package com.villageapp.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.activity_onboarding.*

class OnboardingActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    var adapter: MyPagerAdapter? = null

    override fun getLayoutId() = R.layout.activity_onboarding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = MyPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        pageIndicatorView.setViewPager(viewPager)

        viewPager.addOnPageChangeListener(this)


        onboarding_activity_next.setOnClickListener {

            if (viewPager.currentItem < viewPager.adapter?.count?.minus(1) ?: 0) {

                viewPager.currentItem += 1
            }


        }
        onboarding_activity_prev.setOnClickListener { viewPager.currentItem = 0 }

    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {


        when (position) {
            0 -> {
                onboardingactivity_skipnextbutton.visibility = View.VISIBLE
                onboarding_activity_next.visibility = View.VISIBLE
                onboarding_activity_prev.visibility = View.INVISIBLE
            }
            1 -> {
                onboardingactivity_skipnextbutton.visibility = View.VISIBLE
                onboarding_activity_next.visibility = View.VISIBLE
                onboarding_activity_prev.visibility = View.VISIBLE
            }
            2 -> UiUtils.hideViews(onboardingactivity_skipnextbutton)


        }


    }

    override fun onPageScrollStateChanged(state: Int) {

    }


    companion object {
        fun getIntent(context: Context) = Intent(context, OnboardingActivity::class.java)
    }
}

class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return OnboardingFragment.getInstance(position)
    }

    override fun getCount(): Int {
        return 3
    }
}
