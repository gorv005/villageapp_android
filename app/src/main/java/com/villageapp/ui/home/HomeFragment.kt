package com.villageapp.ui.home

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.models.home.Category
import com.villageapp.models.home.HomeResponseModal
import com.villageapp.models.home.Product
import com.villageapp.models.home.Restaurant
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.network.RestResponse
import com.villageapp.ui.catageoryprodlist.ProductListViewModel
import com.villageapp.ui.home.adapter.popularrecipies.AdapterPopulerRecipes
import com.villageapp.ui.home.adapter.recommendedproducts.AdapterProducts
import com.villageapp.ui.home.adapter.recommendedresturants.AdapterPopularRestaurants
import com.villageapp.ui.productdetail.ActivityProductDetailPage
import com.villageapp.ui.user.register.ActivityLogInSignUp
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.architecture.ext.viewModel

class HomeFragment : BaseFragment() {


    val viewModel: HomeViewModel by viewModel()
    val productListViewModel: ProductListViewModel by viewModel()

    private var restaurantEarnPointObj: Restaurant? = null
    private var productToSave: Product? = null

    private var adapterRecipies: AdapterPopulerRecipes? = null
    private var adapterResturants: AdapterPopularRestaurants? = null
    private var adapterProducts: AdapterProducts? = null

    override fun getLayoutId() = R.layout.fragment_home

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showToolLogo(true, null)
        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val manager2 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val manager3 = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        rcy_popular_recipes.layoutManager = manager
        rcy_recommended_products.layoutManager = manager2
        rcy_recommended_resturents.layoutManager = manager3

        adapterRecipies = activity?.let {
            AdapterPopulerRecipes({ show: Category, clickType: Int ->
            }, it)
        }


        setUpAdapterRestaurant()

        adapterProducts = activity?.let {
            AdapterProducts({ product: Product, clickType: Int ->
                when (clickType) {

                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL -> gotToProdDetailPAge(product)
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE -> saveThisProduct(product)
                    else -> gotToProdDetailPAge(product)
                }
            }, it, true)
        }



        rcy_popular_recipes.adapter = adapterRecipies
        rcy_recommended_resturents.adapter = adapterResturants
        rcy_recommended_products.adapter = adapterProducts


        subscribeUi()

        loadData()
    }


    private fun setUpAdapterRestaurant() {

        val callback = object : AdapterViewClickListener<Restaurant> {
            override fun onClickAdapterView(objectAtPosition: Restaurant, viewType: Int, position: Int) {

                when (viewType) {
                    Config.AdapterClickViewTypes.CLICK_VIEW_WHOLE -> {
                    }
                    Config.AdapterClickViewTypes.CLICK_VIEW_EARN_FREE_POINT -> {

                        if (viewModel.isUserLoggedIn()) {
                            openRestaurantBSEarnPoints(objectAtPosition)
                        } else {

                            restaurantEarnPointObj = objectAtPosition
                            activity?.let {
                                startActivityForResult(
                                    ActivityLogInSignUp.getIntent(it),
                                    ActivityLogInSignUp.REQUEST_CODE_RESTAURANT_EARN_POINTS
                                )

                            }
                        }


                    }
                }
            }
        }



        adapterResturants = activity?.let {

            AdapterPopularRestaurants(callback, it)
        }
    }

    private fun openRestaurantBSEarnPoints(objectAtPosition: Restaurant) {
        val bottomSheetConsumeMealInRestaurant =
            BottomSheetConsumeMealInRestaurant.getInstance(objectAtPosition)
        bottomSheetConsumeMealInRestaurant.show(
            childFragmentManager,
            "BottomSheetConsumeMealInRestaurant"
        )
    }

    private fun loadData() {
        viewModel.getHomeFeed()
    }

    private fun subscribeUi() {

        viewModel.events.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData(it.data)
            }
        })


        productListViewModel.eventsMarkSaved.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(
                    null,
                    message = AndroidUtils.getString(R.string.loading)
                )

                RestResponse.Status.ERROR -> showErrorViewMarkSave(it)

                RestResponse.Status.SUCCESS -> showDataMarkSavedUnsaved(true)
            }
        })


        productListViewModel.eventsRemoveSavedProduct.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(
                    null,
                    message = AndroidUtils.getString(R.string.loading)
                )

                RestResponse.Status.ERROR -> showErrorViewMarkSave(it)

                RestResponse.Status.SUCCESS -> showDataMarkSavedUnsaved(false)
            }
        })
    }


    private fun showErrorViewMarkSave(it: RestResponse<ResponseMarkFavourite>) {

        hideProgressDialog()

        showSnackbar(it.getErrorMessage(), false)
    }

    private fun showDataMarkSavedUnsaved(isFav: Boolean) {

        hideProgressDialog()

        productToSave?.is_favorite = isFav
        adapterProducts?.notifyDataSetChanged()
    }

    private fun showData(data: HomeResponseModal?) {
        showContentView()
        adapterProducts?.submitList(data?.data?.products)
        adapterRecipies?.submitList(data?.data?.categories)
        adapterResturants?.submitList(data?.data?.restaurants)

    }

    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()

    }

    fun notifyMealConsumed(restaurant: Restaurant?) {

        restaurant?.let { adapterResturants?.notifyMealConsumed(it) }

    }


    companion object {

        fun getInstance(): HomeFragment {
            val bundle = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> {

                when (requestCode) {
                    ActivityLogInSignUp.REQUEST_CODE_RESTAURANT_EARN_POINTS -> {
//                        restaurantEarnPointObj?.let { openRestaurantBSEarnPoints(it) }

                        loadData()
                    }

                    ActivityLogInSignUp.REQUEST_CODE_SAVE_PRODUCT -> {
//                        saveThisProduct(productToSave)

                        loadData()
                    }


                }

            }
        }
    }


    private fun gotToProdDetailPAge(product: Product) {

        activity?.let {

            startActivity(ActivityProductDetailPage.getIntent(activity, product.id, product.name))
        }
    }


    private fun saveThisProduct(product: Product?) {

        productToSave = product

        if (viewModel.repository.isUserLoggedIn()) {

            product?.let {

                if (false == it.is_favorite) {

                    it.id?.let { it1 -> productListViewModel.marProductSaved(it1) }
                } else {
                    it.id?.let { it1 -> productListViewModel.removeProductSaved(it1) }

                }
            }

        } else {

            activity?.let {
                startActivityForResult(
                    ActivityLogInSignUp.getIntent(it),
                    ActivityLogInSignUp.REQUEST_CODE_SAVE_PRODUCT
                )
            }


        }

    }
}