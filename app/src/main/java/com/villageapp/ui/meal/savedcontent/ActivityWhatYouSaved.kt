package com.villageapp.ui.meal.savedcontent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.models.meal.consume.list.DataMealFacts
import com.villageapp.ui.meal.consume.ActivitySelectMealConsume
import kotlinx.android.synthetic.main.activity_what_u_saved.*
import org.parceler.Parcels

class ActivityWhatYouSaved : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_what_u_saved


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var mealFacts =
            Parcels.unwrap<DataMealFacts>(intent?.getParcelableExtra<Parcelable>(ActivitySelectMealConsume.KEY_SAVED_DATA_ON_MEAL_CONSUMPTION))

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rcy_saved_content.layoutManager = manager

        val adapterSavedContentOnMealConsume = AdapterSavedContentOnMealConsume()
        rcy_saved_content.adapter = adapterSavedContentOnMealConsume

        adapterSavedContentOnMealConsume.submitList(mealFacts?.meal_facts)


        iv_close.setOnClickListener { finish() }
    }

    companion object {
        fun getIntent(
            context: Context,
            mealFacts: DataMealFacts?
        ): Intent {

            val intent = Intent(context, ActivityWhatYouSaved::class.java)
            val bundle = Bundle()

            if (mealFacts != null)
                bundle.putParcelable(
                    ActivitySelectMealConsume.KEY_SAVED_DATA_ON_MEAL_CONSUMPTION,
                    Parcels.wrap(mealFacts)
                )

            intent.putExtras(bundle)

            return intent
        }

    }

}