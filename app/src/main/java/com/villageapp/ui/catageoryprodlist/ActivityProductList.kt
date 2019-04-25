package com.villageapp.ui.catageoryprodlist

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.models.home.Product
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.models.product.list.ResponseProductList
import com.villageapp.network.RestResponse
import com.villageapp.ui.home.adapter.recommendedproducts.AdapterProducts
import com.villageapp.ui.productdetail.ActivityProductDetailPage
import com.villageapp.ui.user.register.ActivityLogInSignUp
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import com.villageapp.utils.EdgeDecorator
import kotlinx.android.synthetic.main.activity_cat_product_list.*
import org.koin.android.architecture.ext.viewModel

class ActivityProductList : BaseActivity() {


    val viewModel: ProductListViewModel by viewModel()

    private var productToSave: Product? = null


    override fun getLayoutId() = R.layout.activity_cat_product_list

    private var adapter: AdapterProducts? = null

    private var catageoryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showToolBackButtonAndFinishOnClick()



        catageoryId = intent.getIntExtra(KEY_CATAGEORY_ID_RECEPIES, -1)
        val catName = intent.getStringExtra(KEY_CATAGEORY_NAME)
        setToolTitle(catName)


        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcy_category_prod_list.layoutManager = manager
        rcy_category_prod_list.addItemDecoration(EdgeDecorator(AndroidUtils.getDimension(R.dimen.margin_xlarge)))

        adapter = AdapterProducts({ product: Product, clickType: Int ->
            when (clickType) {

                Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL -> gotToProdDetailPAge(product)
                Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE -> saveThisProduct(product)
                else -> gotToProdDetailPAge(product)

            }
        }, this, false)

        rcy_category_prod_list.adapter = adapter

        subscribeUi()
        subscribeUiMarkSave()

        loadData()

    }


    private fun saveThisProduct(product: Product?) {

        productToSave = product
        if (viewModel.repository.isUserLoggedIn()) {

            product?.id?.let { viewModel.marProductSaved(it) }

        } else {


            startActivityForResult(ActivityLogInSignUp.getIntent(this), ActivityLogInSignUp.REQUEST_CODE_SAVE_PRODUCT)

        }

    }

    private fun loadData() {

        viewModel.getProductListFromCatageory(catageoryId)

    }

    private fun subscribeUi() {
        viewModel.events.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData(it.data)
            }
        })
    }


    private fun subscribeUiMarkSave() {
        viewModel.eventsMarkSaved.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showProgressDialog(null,message = AndroidUtils.getString(R.string.loading))

                RestResponse.Status.ERROR -> showErrorViewMarkSave(it)

                RestResponse.Status.SUCCESS -> showDataMarkSaved(it.data)
            }
        })
    }

    private fun showErrorViewMarkSave(it: RestResponse<ResponseMarkFavourite>) {

        hideProgressDialog()

        showSnackbar(it.getErrorMessage(), false)
    }

    private fun showDataMarkSaved(data: ResponseMarkFavourite?) {

        hideProgressDialog()

        productToSave?.is_favorite = true
        adapter?.notifyDataSetChanged()
    }

    private fun showData(data: ResponseProductList?) {

        showContentView()
        adapter?.submitList(data?.data?.products)

    }

    private fun gotToProdDetailPAge(product: Product) {
        startActivity(ActivityProductDetailPage.getIntent(this, product.id, product.name))
    }


    companion object {
        val KEY_CATAGEORY_ID_RECEPIES = "KEY_CATAGEORY_ID_RECEPIES"
        val KEY_CATAGEORY_NAME = "KEY_CATAGEORY_NAME"

        fun getIntent(context: Context, catageoryId: Int, catName: String?): Intent {

            val intent = Intent(context, ActivityProductList::class.java)

            intent.putExtra(KEY_CATAGEORY_ID_RECEPIES, catageoryId)
            intent.putExtra(KEY_CATAGEORY_NAME, catName)

            return intent
        }
    }

    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == ActivityLogInSignUp.REQUEST_CODE_SAVE_PRODUCT) {
                saveThisProduct(productToSave)
            }

        }

    }

}