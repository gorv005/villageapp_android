package com.villageapp.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import com.villageapp.R
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils

abstract class BaseBottomSheetFragment : BottomSheetDialogFragment() {


    abstract fun getLayoutId(): Int


    private lateinit var rootView: View

    private val transparentBackground: Boolean = true

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val layoutId = getLayoutId()
        rootView = LayoutInflater.from(context).inflate(layoutId, null)


        try {
            dialog.setContentView(rootView)
            if (transparentBackground) rootView.background = ColorDrawable(AndroidUtils.getColor(R.color.transparent))
        } catch (e: Exception) {
        }


        return dialog
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init(rootView, savedInstanceState)
    }

    abstract fun init(rootView: View?, savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }


    fun showSnackbar(string: String?, positive: Boolean) {
        UiUtils.showSnackBar(activity, string, positive)
    }

    fun showProgressDialog(title: String?, message: String?, cancelable: Boolean = false) {
        UiUtils.showProgressDialog(context, title, message, cancelable)
    }

    fun hideProgressDialog() {
        UiUtils.dismissProgressDialog()
    }


}