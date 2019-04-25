package com.villageapp.ui.productdetail.directions

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.models.product.detail.ResponseModalProductDetail
import com.villageapp.network.RestResponse
import com.villageapp.ui.productdetail.ProductDetailViewModel
import kotlinx.android.synthetic.main.fragment_product_direction.*
import org.koin.android.architecture.ext.sharedViewModel

class FragmentProductDirections : BaseFragment() {

    val viewModel: ProductDetailViewModel by sharedViewModel()


    override fun getLayoutId(): Int = R.layout.fragment_product_direction


    private var adapterDirections: AdapterProductDetailDirections? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcy_prod_direction.layoutManager = manager
        adapterDirections = activity?.let {
            AdapterProductDetailDirections()
        }
        rcy_prod_direction.adapter = adapterDirections

        subscribeUi()

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
    }

    private fun showData(data: ResponseModalProductDetail?) {

        adapterDirections?.submitList(data?.data?.product?.product_directions)
    }


    companion object {

        fun getInstance(): FragmentProductDirections {
            val bundle = Bundle()

            val fragment = FragmentProductDirections()
            fragment.arguments = bundle
            return fragment
        }
    }
}