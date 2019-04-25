package com.villageapp.ui.productdetail.summary

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.ProductDetailPageInterface
import com.villageapp.models.home.Product
import com.villageapp.models.product.detail.ResponseModalProductDetail
import com.villageapp.models.product.marksave.ResponseMarkFavourite
import com.villageapp.network.RestResponse
import com.villageapp.ui.catageoryprodlist.ProductListViewModel
import com.villageapp.ui.home.adapter.recommendedproducts.AdapterProducts
import com.villageapp.ui.productdetail.ActivityProductDetailPage
import com.villageapp.ui.productdetail.ProductDetailViewModel
import com.villageapp.ui.user.register.ActivityLogInSignUp
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.Config
import kotlinx.android.synthetic.main.fragment_product_summary.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel

class FragmentProductSummary : BaseFragment() {

    val viewModel: ProductDetailViewModel by sharedViewModel()

    val productListViewModel: ProductListViewModel by viewModel()

    private var productToSave: Product? = null


    override fun getLayoutId(): Int = R.layout.fragment_product_summary


    private var adapterProducts: AdapterProducts? = null


    private var productDetailPageInterface: ProductDetailPageInterface? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ProductDetailPageInterface) {
            productDetailPageInterface = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ViewCompat.setNestedScrollingEnabled(rcy_related_recipies, false)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcy_related_recipies.layoutManager = manager
        adapterProducts = activity?.let {
            AdapterProducts({ product: Product, clickType: Int ->
                when (clickType) {

                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_DETAIL -> gotToProdDetailPAge(product)
                    Config.AdapterClickViewTypes.CLICK_VIEW_PRODUCT_SAVE -> saveThisProduct(product)
                    else -> gotToProdDetailPAge(product)
                }
            }, it, true)
        }
        rcy_related_recipies.adapter = adapterProducts


        subscribeUi()

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

    private fun subscribeUi() {

        viewModel.events.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> {
                }

                RestResponse.Status.ERROR -> {
                }

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

    private fun showData(data: ResponseModalProductDetail?) {

        val product = data?.data?.product
        tv_prod_name.text = product?.name
        tv_prod_cooking_time.text = product?.cooking_time
        tv_prod_desc.text = product?.summary

        adapterProducts?.submitList(data?.data?.related_products)
    }


    companion object {

        fun getInstance(): FragmentProductSummary {
            val bundle = Bundle()

            val fragment = FragmentProductSummary()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            Activity.RESULT_OK -> {

                when (requestCode) {


                    ActivityLogInSignUp.REQUEST_CODE_SAVE_PRODUCT -> {
                        productDetailPageInterface?.refreshData()
                    }


                }

            }
        }
    }
}