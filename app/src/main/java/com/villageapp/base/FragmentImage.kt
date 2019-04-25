package com.villageapp.base

import android.os.Bundle
import com.villageapp.R
import com.villageapp.managers.ImageRequestManager
import kotlinx.android.synthetic.main.fragment_product_image.*

class FragmentImage : BaseFragment() {


    override fun getLayoutId(): Int = R.layout.fragment_product_image


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        ImageRequestManager.with(sv_prod)
            .url(arguments?.getString(KEY_IMAGE_URL, ""))
            .build()

    }


    companion object {

        val KEY_IMAGE_URL = "KEY_IMAGE_URL"
        fun getInstance(image: String?): FragmentImage {
            val bundle = Bundle()

            bundle.putString(KEY_IMAGE_URL, image)
            val fragment = FragmentImage()
            fragment.arguments = bundle

            return fragment
        }
    }
}