package com.villageapp.ui.meal.consume

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.models.meal.consume.list.ResponseMealConsumedList
import com.villageapp.models.meal.search.Meal
import com.villageapp.network.RestResponse
import com.villageapp.ui.home.BottomSheetConsumeMealInRestaurant
import com.villageapp.ui.home.ViewModelMeal
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.activity_select_meals_to_consume.*
import org.koin.android.architecture.ext.viewModel
import org.parceler.Parcels


class ActivitySelectMealConsume : BaseActivity(), View.OnClickListener {


    val viewModel: ViewModelMeal by viewModel()

    companion object {

        const val REQUEST_CODE_MULTIPLE_MEAL_CONSUMED = 99
        const val KEY_SAVED_DATA_ON_MEAL_CONSUMPTION = "KEY_SAVED_DATA_ON_MEAL_CONSUMPTION"
        const val KEY_POINT_EARNED_ON_MEAL_CONSUMPTION = "KEY_POINT_EARNED_ON_MEAL_CONSUMPTION"

        fun getIntent(context: Context): Intent {
            return Intent(context, ActivitySelectMealConsume::class.java)

        }
    }

    override fun getLayoutId() = R.layout.activity_select_meals_to_consume


    private lateinit var adapterMeals: AdapterSearchMeals

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapterMeals = AdapterSearchMeals()

        edt_search_meals.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                retryButtonClick()
                return@OnEditorActionListener true
            }
            false
        })

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_searched_meals.layoutManager = manager
        rv_searched_meals.adapter = adapterMeals


        iv_search_meal.setOnClickListener(this)
        iv_close.setOnClickListener(this)
        tv_submit.setOnClickListener(this)

        subscribeUi()

        retryButtonClick()


    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.iv_search_meal -> {

                retryButtonClick()
            }
            R.id.iv_close -> {
                finish()
            }
            R.id.tv_submit -> {

                submitConsumedMeals()
            }
        }
    }

    private fun subscribeUi() {

        viewModel.eventsMealSearch.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData(it.data?.data?.meals)
            }
        })


        viewModel.eventsPostMealConsumeList.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(null, AndroidUtils.getString(R.string.loading))

                RestResponse.Status.ERROR -> {
                    hideProgressDialog()

                    UiUtils.showSnackBar(
                        this,
                        it.getErrorMessage(), false
                    )
                }

                RestResponse.Status.SUCCESS -> {
                    hideProgressDialog()

                    onSuccessOfMealConsumed(it)

                }
            }
        })
    }

    private fun onSuccessOfMealConsumed(response: RestResponse<ResponseMealConsumedList>?) {


        val bundle = Bundle()

        val mealFacts = response?.data?.data

        if (!(mealFacts == null || mealFacts.meal_facts.isNullOrEmpty())) {
            bundle.putParcelable(KEY_SAVED_DATA_ON_MEAL_CONSUMPTION, Parcels.wrap(mealFacts))
            bundle.putInt(KEY_POINT_EARNED_ON_MEAL_CONSUMPTION, adapterMeals.getPointsEarned())
        }

        val mIntent = Intent()
        mIntent.putExtras(bundle)


        setResult(Activity.RESULT_OK, mIntent)
        finish()
    }

    override fun retryButtonClick() {
        super.retryButtonClick()
        resetAndSearchQuery()
    }

    private fun resetAndSearchQuery() {

        UiUtils.hideSoftKeyboard(this)

        val query = edt_search_meals.text.trim().toString()

        resetData()
        viewModel.searchMealList(query)

    }

    private fun resetData() {

        adapterMeals.submitList(null)

    }


    private fun showData(meals: List<Meal>?) {

        if (meals.isNullOrEmpty()) {
            showEmptyView("Oops! No meal found", "")

        } else {
            showContentView()
            adapterMeals.submitList(meals)
        }

    }

    private fun submitConsumedMeals() {

        val mealIds: String? = adapterMeals.getSelectedMealIds()

        if (mealIds == null) {

            UiUtils.showSnackBar(this, "Please select meals you have consumed.", false)

        } else {

            viewModel.postMealConsumeList(mealIds)
        }


    }
}