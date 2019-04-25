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

class BottomSheetDialogChangeRemoveUserImage : BaseBottomSheetFragment() {


    private var callBackProfilePicChoose: CallBackProfilePicChoose? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (parentFragment is CallBackProfilePicChoose) {

            callBackProfilePicChoose = parentFragment as CallBackProfilePicChoose
        }

    }

    override fun getLayoutId() = R.layout.bs_change_remove_user_image

    override fun init(rootView: View?, savedInstanceState: Bundle?) {

        rootView?.findViewById<TextView>(R.id.tv_bs_remove_picture)?.setOnClickListener {
            dismiss()

            callBackProfilePicChoose?.removePicture()

        }

        rootView?.findViewById<TextView>(R.id.tv_bs_upload)?.setOnClickListener {

            dismiss()

            callBackProfilePicChoose?.uploadPicture()

        }


    }
}