package com.villageapp.ui.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.models.home.search.Result
import com.villageapp.network.RestResponse
import com.villageapp.ui.catageoryprodlist.ActivityProductList
import com.villageapp.ui.productdetail.ActivityProductDetailPage
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import com.villageapp.utils.UiUtils
import kotlinx.android.synthetic.main.activity_select_meals_to_consume.*
import org.koin.android.architecture.ext.viewModel

class FragmentHomeSearch : BaseFragment(), AdapterViewClickListener<Result> {


    override fun onClickAdapterView(objectAtPosition: Result, viewType: Int, position: Int) {

        when (viewType) {
            Config.AdapterClickViewTypes.CLICK_VIEW_RECIPE_OR_CATAGEORY -> {
                val intent = objectAtPosition.resource?.id?.let {
                    activity?.let { it1 ->
                        ActivityProductList.getIntent(
                            it1, it, objectAtPosition.resource.name
                        )
                    }
                }
                intent?.let { activity?.startActivity(it) }
            }

            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL -> {

                objectAtPosition.resource?.id?.let {

                    activity?.let {
                        startActivity(ActivityProductDetailPage.getIntent(activity, objectAtPosition.resource.id, objectAtPosition.resource.name))

                    }
                }


            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE -> {
                /*removeSavedProduct(product)*/
            }

            //Restaurant Card Click Listeners
            Config.AdapterClickViewTypes.CLICK_VIEW_WHOLE -> {
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_EARN_FREE_POINT -> {
            }

        }
    }


    val viewModel: ViewModelHomeSearch by viewModel()

    private lateinit var adapterHomeSearch: AdapterHomeSearch


    override fun getLayoutId() = R.layout.fragment_home_search

    companion object {


        fun getInstance(): FragmentHomeSearch {
            val bundle = Bundle()

            val fragment = FragmentHomeSearch()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setToolTitle(AndroidUtils.getString(R.string.search))

        adapterHomeSearch = AdapterHomeSearch(this)

        edt_search_meals.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                retryButtonClick()
                return@OnEditorActionListener true
            }
            false
        })

        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_searched_meals.layoutManager = manager
        rv_searched_meals.adapter = adapterHomeSearch


        iv_search_meal.setOnClickListener { retryButtonClick() }

        subscribeUi()

    }


    override fun retryButtonClick() {
        super.retryButtonClick()
        resetAndSearchQuery()
    }

    private fun resetAndSearchQuery() {


        val query = edt_search_meals.text.trim().toString()

        if (!query.isEmpty()) {

            activity?.let { UiUtils.hideSoftKeyboard(it) }
            resetData()
            viewModel.getHomeSearched(query)

        } else {

            UiUtils.showToast(activity, AndroidUtils.getString(R.string.error_search_field_cant_blank))

        }


    }

    private fun resetData() {

        adapterHomeSearch.submitList(null)

    }


    private fun showData(searchList: List<Result>?) {

        if (searchList.isNullOrEmpty()) {
            showEmptyView("Oops! No result found", "Please try to search something else")

        } else {
            showContentView()
            adapterHomeSearch.submitList(searchList)
        }

    }


    private fun subscribeUi() {

        viewModel.eventHomeSearch.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData(it.data?.data?.results)
            }
        })


    }


}