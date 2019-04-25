package com.villageapp.ui.home

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.villageapp.R
import com.villageapp.base.BaseBottomSheetFragment
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.home.Restaurant
import com.villageapp.models.meal.consume.inrestaurant.ResponseMarkMealConsumedInRestaurant
import com.villageapp.network.RestResponse
import com.villageapp.utils.AndroidUtils
import org.koin.android.architecture.ext.viewModel
import org.parceler.Parcels


class BottomSheetConsumeMealInRestaurant : BaseBottomSheetFragment() {

    val viewModel: ViewModelMeal by viewModel()

    private var points: Int? = 0

    private var tvConsumeMealRestaurantName: TextView? = null
    private var svRestaurantImage: SimpleDraweeView? = null
    private var llMealConsumed: LinearLayout? = null
    private var tvConsumeMeal: TextView? = null
    private var tvEarnPoints: TextView? = null

    var restaurant: Restaurant? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun getLayoutId() = R.layout.bs_home_earn_point_restaurant

    override fun init(rootView: View?, savedInstanceState: Bundle?) {


        restaurant = Parcels.unwrap<Restaurant>(arguments?.getParcelable<Parcelable>(KEY_RESTAURANT))


        tvConsumeMealRestaurantName = rootView?.findViewById<TextView>(R.id.tv_consume_meal_in_resturant)
        svRestaurantImage = rootView?.findViewById<SimpleDraweeView>(R.id.sv_restaurant)
        llMealConsumed = rootView?.findViewById<LinearLayout>(R.id.ll_meal_consumed)
        tvConsumeMeal = rootView?.findViewById<TextView>(R.id.tv_meal_consumed)
        tvEarnPoints = rootView?.findViewById<TextView>(R.id.tv_earn_free_points)


        restaurant?.let { it ->

            points = it.points
            val imageCornerRadius: Float = AndroidUtils.getDimension(R.dimen.margin_medium).toFloat()


            tvConsumeMealRestaurantName?.text =
                    AndroidUtils.getString(R.string.consume_meal_resturant_name, it.name)


            ImageRequestManager.with(svRestaurantImage).url(it.image).roundedCornerRadius(imageCornerRadius).build()

            mealConsumed(false)

            it.is_meal_consumed?.let { it1 ->
                if (it1) {

                    mealConsumed(true)


                } else {
                    mealConsumed(false)

                }
            }

            rootView?.findViewById<View>(R.id.ll_meal_consumed)?.setOnClickListener { _ ->


                it.is_meal_consumed?.let { it1 ->
                    if (it1) {


                    } else {
                        it.id?.let(viewModel::markMealConsumedInRestaurant)

                    }
                }


            }


        }

        subscribeUi()


    }

    private fun subscribeUi() {
        viewModel.events.observe(this, Observer
        {

            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData()
            }

        })
    }


    companion object {


        private const val KEY_RESTAURANT = "KEY_RESTAURANT"

        fun getInstance(restaurant: Restaurant): BottomSheetConsumeMealInRestaurant {
            val bottomSheetConsumeMealInRestaurant = BottomSheetConsumeMealInRestaurant()
            val args = Bundle()
            args.putParcelable(KEY_RESTAURANT, Parcels.wrap(restaurant))
            bottomSheetConsumeMealInRestaurant.arguments = args
            return bottomSheetConsumeMealInRestaurant
        }
    }


    private fun showData() {
        restaurant?.is_meal_consumed = true
        hideProgressDialog()
        mealConsumed(true)
        notifyParentMealConsumed()


    }

    private fun notifyParentMealConsumed() {

        if (parentFragment is HomeFragment) {
            val homeFragment = parentFragment as HomeFragment
            homeFragment.notifyMealConsumed(restaurant)

        }

    }

    private fun showErrorView(it: RestResponse<ResponseMarkMealConsumedInRestaurant>) {
        hideProgressDialog()
        dismiss()
        showSnackbar(it.getErrorMessage(), false)

    }

    private fun showLoadingView() {

        showProgressDialog(null, AndroidUtils.getString(R.string.loading))

    }


    private fun mealConsumed(isMealConsumed: Boolean) {

        if (isMealConsumed) {
            llMealConsumed?.background =
                    activity?.resources?.getDrawable(R.drawable.rounded_corners_app_fafafa_button)

            activity?.resources?.getColor(R.color.black)?.let { tvConsumeMeal?.setTextColor(it) }

            tvEarnPoints?.text =
                    AndroidUtils.getString(R.string.earned_x_points, points)
        } else {
            llMealConsumed?.background =
                    activity?.resources?.getDrawable(R.drawable.rounded_corners_app_green_button)

            tvEarnPoints?.text =
                    AndroidUtils.getString(R.string.earn_x_points, points)

        }
    }


}