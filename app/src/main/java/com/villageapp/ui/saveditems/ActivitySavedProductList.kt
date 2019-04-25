package com.villageapp.ui.saveditems

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.callbacks.AdapterViewClickListener
import com.villageapp.models.home.Product
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.models.user.favouritelist.ResponseUserFavouriteList
import com.villageapp.models.user.favouritelist.UserSavedFavourites
import com.villageapp.network.RestResponse
import com.villageapp.ui.catageoryprodlist.ProductListViewModel
import com.villageapp.ui.dailyalerts.ViewModelDailyAlerts
import com.villageapp.ui.dailyalerts.detail.ActivityAlertDetailPage
import com.villageapp.ui.productdetail.ActivityProductDetailPage
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.activity_cat_product_list.*
import org.koin.android.architecture.ext.viewModel

class ActivitySavedProductList : BaseActivity(), AdapterViewClickListener<UserSavedFavourites> {


    private val viewModel: SavedProductListViewModel by viewModel()
    private val viewModelDailyAlert: ViewModelDailyAlerts by viewModel()
    private val productListViewModel: ProductListViewModel by viewModel()

    private var positionForUnMarkDailyAlert: Int? = -1
    private var positionForUnMarkProduct: Int? = -1

    private var adapter: AdapterUserSavedListing? = null
    private val savedItems: ArrayList<UserSavedFavourites> = ArrayList();


    override fun getLayoutId() = R.layout.activity_cat_product_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showToolBackButtonAndFinishOnClick()
        setToolTitle(AndroidUtils.getString(R.string.tool_title_saved_items))

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcy_category_prod_list.layoutManager = manager
//        rcy_category_prod_list.addItemDecoration(EdgeDecorator(AndroidUtils.getDimension(R.dimen.margin_xlarge)))

        adapter = AdapterUserSavedListing(this)

        rcy_category_prod_list.adapter = adapter

        subscribeUi()

    }

    private fun removeSavedProduct(product: Product) {
        if (viewModel.repository.isUserLoggedIn()) {

            product?.let {

                it.id?.let { it1 -> productListViewModel.removeProductSaved(it1) }

            }

        }
    }

    private fun loadData() {

        viewModel.getSavedProdDailyAlertList()

    }

    private fun subscribeUi() {
        viewModel.events.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData(it.data)
            }
        })


        productListViewModel.eventsRemoveSavedProduct.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(
                    null,
                    message = AndroidUtils.getString(R.string.loading)
                )

                RestResponse.Status.ERROR -> showErrorViewMarkSave(it)

                RestResponse.Status.SUCCESS -> showDataMarkSavedUnsavedProduct(false)
            }
        })

        viewModelDailyAlert.eventDailyAlertFavouriteRemove.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(
                    null,
                    message = AndroidUtils.getString(R.string.loading)
                )

                RestResponse.Status.ERROR -> showErrorViewMarkSave(it)

                RestResponse.Status.SUCCESS -> showDataMarkSavedUnsavedALert(false)
            }
        })
    }


    private fun showErrorViewMarkSave(it: RestResponse<ResponseMarkFavourite>) {

        hideProgressDialog()

        showSnackbar(it.getErrorMessage(), false)
    }

    private fun showDataMarkSavedUnsavedProduct(isFav: Boolean) {

        refreshList(positionForUnMarkProduct)
    }

    private fun showDataMarkSavedUnsavedALert(isFav: Boolean) {
        refreshList(positionForUnMarkDailyAlert)
    }


    fun refreshList(positionToRemove: Int?) {

        hideProgressDialog()

        try {
            if (!savedItems.isNullOrEmpty() && positionToRemove != -1) {

                positionToRemove?.let { savedItems.removeAt(it) }
                adapter?.submitList(savedItems)
                adapter?.notifyDataSetChanged()
            }
        } catch (e: Exception) {
        }


        if (savedItems.isNullOrEmpty()) {
            showEmptyView(AndroidUtils.getString(R.string.no_saved_item_found), "")

        }
    }


    private fun showData(data: ResponseUserFavouriteList?) {
        showContentView()

        savedItems?.clear()

        data?.data?.let {
            if (!it.daily_alerts.isNullOrEmpty()) {
                for (obj in it.daily_alerts) {
                    val userSavedObj = UserSavedFavourites(obj, null, UserSavedFavourites.DAILY_ALERTS)
                    savedItems.add(userSavedObj)
                }

            }

            if (!it.products.isNullOrEmpty()) {
                for (obj in it.products) {
                    val userSavedObj = UserSavedFavourites(null, obj, UserSavedFavourites.PRODUCT)
                    savedItems.add(userSavedObj)
                }
            }
        }

        if (savedItems.isNullOrEmpty()) {
            showEmptyView(AndroidUtils.getString(R.string.no_saved_item_found), "")
        } else {

            adapter?.submitList(savedItems)
        }

    }

    private fun gotToProdDetailPAge(product: Product) {
        startActivity(ActivityProductDetailPage.getIntent(this, product.id, product.name))
    }


    companion object {

        fun getIntent(context: Context): Intent {

            return Intent(context, ActivitySavedProductList::class.java)
        }
    }

    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()
    }


    override fun onClickAdapterView(objectAtPosition: UserSavedFavourites, viewType: Int, position: Int) {
        when (viewType) {

            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL -> objectAtPosition.products?.let {
                gotToProdDetailPAge(
                    it
                )
            }
            Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE -> objectAtPosition.products?.let {

                this.positionForUnMarkProduct = position
                removeSavedProduct(it)

            }

            Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_MARK_FAV_OR_UNFAV -> objectAtPosition.dailyAlerts?.id?.let {

                this.positionForUnMarkDailyAlert = position
                viewModelDailyAlert.removeFavouriteDailyAlert(it)

            }

            Config.AdapterClickViewTypes.CLICK_VIEW_DAILY_ALERT_WHOLE -> objectAtPosition.dailyAlerts?.let {
                this.positionForUnMarkDailyAlert = position
                startActivityForResult(
                    ActivityAlertDetailPage.getIntent(
                        this,
                        it.id,
                        it.title,
                        position
                    ), ActivityAlertDetailPage.FROM_DETAIL_PAGE
                )
            }

        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (resultCode) {
//
//
//            Activity.RESULT_OK -> {
//
//
//                when (requestCode) {
//                    ActivityAlertDetailPage.FROM_DETAIL_PAGE -> {
//
//                        data?.let {
//                            val alertId = it.getIntExtra(ActivityAlertDetailPage.KEY_DAILY_ALERT_ID, -1)
//                            val adpPosition = it.getIntExtra(ActivityAlertDetailPage.KEY_DAILY_LIST_ADP_POSITION, -1)
//                            val isFavorite = it.getBooleanExtra(ActivityAlertDetailPage.KEY_DETAIL_DAILY_IS_FAV, false)
//
//                            if (alertId != -1 && adpPosition != -1 && !isFavorite) {
//
//                                showDataMarkSavedUnsavedALert(isFavorite)
//                            }
//
//                        }
//
//
//                    }
//
//                }
//
//            }
//        }
//    }


    override fun onResume() {
        super.onResume()

        loadData()
    }

}