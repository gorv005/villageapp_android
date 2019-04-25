package com.villageapp.ui.user.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.villageapp.R
import com.villageapp.base.BaseBottomSheetFragment
import com.villageapp.callbacks.CallBackProfilePicChoose
import kotlinx.android.synthetic.main.bs_change_remove_user_image.*
import org.w3c.dom.Text

class BottomSheetDialogPointsDescription : BaseBottomSheetFragment() {





    override fun getLayoutId() = R.layout.bs_change_earned_points_description

    override fun init(rootView: View?, savedInstanceState: Bundle?) {

        rootView?.findViewById<TextView>(R.id.tv_dismiss)?.setOnClickListener {
            dismiss()

        }


    }
}