package com.villageapp.ui.productdetail

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.base.ImagesPagerAdapter
import com.villageapp.callbacks.ProductDetailPageInterface
import com.villageapp.models.product.detail.ResponseModalProductDetail
import com.villageapp.network.RestResponse
import com.villageapp.ui.productdetail.adapter.ProductDetailViewPagerAdapter
import kotlinx.android.synthetic.main.activity_product_detail.*
import org.koin.android.architecture.ext.viewModel

class ActivityProductDetailPage : BaseActivity(), ProductDetailPageInterface {
    override fun refreshData() {
        loadData()
    }

    val viewModel: ProductDetailViewModel by viewModel()


    private var productName: String? = null
    private var pagerAdapter: ProductDetailViewPagerAdapter? = null
    private var productId: Int? = 0

    companion object {
        const val KEY_PRODUCT_ID = "KEY_PRODUCT_ID"

        const val KEY_PRODUCT_NAME = "KEY_PRODUCT_NAME"
        fun getIntent(context: Context?, id: Int?, productName: String?): Intent? {
            if (context == null) {
                return null
            }

            return Intent(context, ActivityProductDetailPage::class.java)
                .putExtra(KEY_PRODUCT_ID, id ?: 0)
                .putExtra(KEY_PRODUCT_NAME, productName)
        }

    }


    override fun getLayoutId() = R.layout.activity_product_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        productName = intent?.getStringExtra(KEY_PRODUCT_NAME)
        productId = intent?.getIntExtra(KEY_PRODUCT_ID, -1)

        showToolBackButtonAndFinishOnClick()
        setToolTitle(productName)

        setupUi()

        subscribeUi()

        loadData()
    }


    private fun loadData() {

        productId?.let { viewModel.getProductDetail(it) }

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


    private fun showData(data: ResponseModalProductDetail?) {

        showContentView()

        /* data?.data?.product?.images = listOf(
             "https://www.fruitsandveggiesmorematters.org/wp-content/uploads/UserFiles/Images/iv_090616_image.jpg",
             "https://www.fruitsandveggiesmorematters.org/wp-content/uploads/UserFiles/Images/iv_090616_image.jpg",
             "https://www.fruitsandveggiesmorematters.org/wp-content/uploads/UserFiles/Images/iv_090616_image.jpg")*/


        if (productName == null || productName.isNullOrBlank()) {
            productName = data?.data?.product?.name
            setToolTitle(productName)
        }


        data?.data?.product?.images?.let {

            val prodImagesPagerAdapter = ImagesPagerAdapter(supportFragmentManager, it)
            viewPager_productImages.adapter = prodImagesPagerAdapter
            pageIndicatorViewProd.setViewPager(viewPager_productImages)

        }


    }


    private fun setupUi() {

//        val params = productImageCollapsingToolbar.layoutParams
//        if (params is AppBarLayout.LayoutParams) {
//            params.height = (UiUtils.getDeviceWidth() / IMAGE_RATIO_EVENT_IMAGE).toInt()
//            productImageCollapsingToolbar.layoutParams = params
//        }

        pagerAdapter = ProductDetailViewPagerAdapter(supportFragmentManager)
        productDetailViewPager.adapter = pagerAdapter
        productDetailTabLayout.setupWithViewPager(productDetailViewPager)


    }

    override fun retryButtonClick() {
        super.retryButtonClick()
        loadData()
    }


}