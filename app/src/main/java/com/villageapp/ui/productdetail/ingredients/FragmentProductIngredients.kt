package com.villageapp.ui.productdetail.ingredients

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.models.product.detail.ResponseModalProductDetail
import com.villageapp.network.RestResponse
import com.villageapp.ui.productdetail.ProductDetailViewModel
import com.villageapp.ui.productdetail.directions.AdapterProductDetailDirections
import kotlinx.android.synthetic.main.fragment_product_direction.*
import kotlinx.android.synthetic.main.fragment_product_ingredients.*
import kotlinx.android.synthetic.main.fragment_product_summary.*
import org.koin.android.architecture.ext.sharedViewModel

class FragmentProductIngredients : BaseFragment() {

    val viewModel: ProductDetailViewModel by sharedViewModel()

    override fun getLayoutId(): Int = R.layout.fragment_product_ingredients


    private var adapterProdIngredient: AdapterProductDetailIngredients? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcy_prod_ingredeints.layoutManager = manager
        adapterProdIngredient = activity?.let {
            AdapterProductDetailIngredients()
        }
        rcy_prod_ingredeints.adapter = adapterProdIngredient

        subscribeUi()

    }

    private fun subscribeUi() {

        viewModel?.events?.observe(this, Observer {
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
        adapterProdIngredient?.submitList(data?.data?.product?.product_ingredients)

    }

    companion object {

        fun getInstance(): FragmentProductIngredients {
            val bundle = Bundle()

            val fragment = FragmentProductIngredients()
            fragment.arguments = bundle
            return fragment
        }
    }
}