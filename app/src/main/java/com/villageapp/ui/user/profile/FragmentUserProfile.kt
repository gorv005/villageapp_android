package com.villageapp.ui.user.profile

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.TextUtils
import android.view.View
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.BottomNavClickCallbacks
import com.villageapp.callbacks.CallBackProfilePicChoose
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.meal.consume.list.DataMealFacts
import com.villageapp.models.user.info.get.ResponseUserInfo
import com.villageapp.network.RestResponse
import com.villageapp.ui.home.HomeActivity
import com.villageapp.ui.meal.consume.ActivitySelectMealConsume
import com.villageapp.ui.meal.savedcontent.ActivityWhatYouSaved
import com.villageapp.ui.saveditems.ActivitySavedProductList
import com.villageapp.ui.user.password.change.ActivityChangePassword
import com.villageapp.utils.AndroidUtils
import com.villageapp.utils.UiUtils
import com.villageapp.utils.copper.CropImage
import com.villageapp.utils.copper.CropImageView
import kotlinx.android.synthetic.main.content_edit_profile.*
import kotlinx.android.synthetic.main.content_user_profile_info.*
import kotlinx.android.synthetic.main.view_email.*
import kotlinx.android.synthetic.main.view_name.*
import kotlinx.android.synthetic.main.view_profile_header.*
import kotlinx.android.synthetic.main.view_profile_score.*
import org.koin.android.architecture.ext.viewModel
import org.parceler.Parcels


class FragmentUserProfile : BaseFragment(), View.OnClickListener, CallBackProfilePicChoose {


    val profileViewModel: ViewModelProfile by viewModel()

    var imageToUpdate: String? = null

    override fun getLayoutId() = R.layout.fragment_profile


    private var bottomNavClickCallbacks: BottomNavClickCallbacks? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is BottomNavClickCallbacks) {
            bottomNavClickCallbacks = context
        }
    }


    private fun setUpUi() {
        showProfileInfoView()

        setToolTitle(AndroidUtils.getString(R.string.profile_menu_edit_profile))
        showToolBackButton(true, View.OnClickListener { showProfileInfoView() })

        setUserDataFromPref()


        //setting click listeners

        tv_user_enter_your_meal.setOnClickListener(this)
        tv_what_is_this.setOnClickListener(this)
        tv_profile_menu_saved_items.setOnClickListener(this)
        tv_profile_menu_change_password.setOnClickListener(this)
//        tv_profile_menu_notification_settings.setOnClickListener(this)
        tv_profile_menu_terms.setOnClickListener(this)
        tv_profile_menu_privacy_policy.setOnClickListener(this)

        tv_user_profile_edit.setOnClickListener(this)
        tv_user_profile_logout.setOnClickListener(this)
        tv_user_profile_delete_Account.setOnClickListener(this)

        //Edit VIew
        tv_change_picture.setOnClickListener(this)
        tv_save_changes.setOnClickListener(this)

        if (profileViewModel.isUserFBLoggedIn()) {
            ll_change_pwd_menu_container.visibility = View.GONE
        }

        edt_email.isEnabled = false
    }

    private fun setUserDataFromPref() {
        ImageRequestManager.with(sv_profile_user_image)
            .setPlaceholderImage(R.drawable.placeholder_image_avatar)
            .url(profileViewModel.getUserImage())
            .circular(true)
            .build()

        tv_profile_user_name?.text = profileViewModel.getUserName()
        tv_profile_user_email?.text = profileViewModel.getUserEmail()
        setEarnedPoints()

    }

    private fun setEarnedPoints() {
        tv_user_earned_points?.text = profileViewModel.getEarnedPoints()?.toString()
    }

    private fun subscribeUi() {
        profileViewModel.eventGetUserInfo.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showData(it.data)
            }
        })


        profileViewModel.eventUpdateUserInfo.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataUserUpdate()
            }
        })

        profileViewModel.eventRemoveUserImage.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataRemoveUserImage()
            }
        })

        profileViewModel.eventDeleteUserProfile.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataDeleteUserProfile()
            }
        })

        profileViewModel.eventLogoutUserServer.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataDeleteUserProfile()
            }
        })
    }

    private fun showDataDeleteUserProfile() {

        hideProgressDialog()
        localLogoutAndGoHome()

    }

    private fun showDataRemoveUserImage() {

        hideProgressDialog()

        profileViewModel.removeUserImagePref()

        ImageRequestManager.with(sv_profile_user_image)
            .url(profileViewModel.getUserImage())
            .setPlaceholderImage(R.drawable.placeholder_image_avatar)
            .circular(true)
            .build()

        ImageRequestManager.with(sv_user_image_edit)
            .url(profileViewModel.getUserImage())
            .setPlaceholderImage(R.drawable.placeholder_image_avatar)
            .circular(true)
            .build()

    }

    private fun showDataUserUpdate() {
        hideProgressDialog()
        showProfileInfoView()
        loadData()
    }

    private fun showData(data: ResponseUserInfo?) {
        hideProgressDialog()
        data?.data?.user?.let {
            profileViewModel.updateUserInfoInPref(it)
            setUserDataFromPref()
        }

    }


    private fun loadData() {
        profileViewModel.getUserInfo()
    }

    override fun onClick(v: View?) {
        activity?.let { UiUtils.hideSoftKeyboard(it) }

        when (v?.id) {
            R.id.tv_user_enter_your_meal -> {

                startActivityForResult(
                    activity?.let { ActivitySelectMealConsume.getIntent(it) },
                    ActivitySelectMealConsume.REQUEST_CODE_MULTIPLE_MEAL_CONSUMED
                )

                activity?.overridePendingTransition(R.anim.slide_in, 0);


            }
            R.id.tv_what_is_this -> {

                val bottomSheetDialogPointsDescription =
                    BottomSheetDialogPointsDescription()
                bottomSheetDialogPointsDescription.show(
                    childFragmentManager,
                    "BottomSheetDialogPointsDescription"
                )

            }
            R.id.tv_profile_menu_saved_items -> {

                startActivity(activity?.let { ActivitySavedProductList.getIntent(it) })

            }
            R.id.tv_profile_menu_change_password -> {

                startActivity(activity?.let { ActivityChangePassword.getIntent(it) })

            }
            /* R.id.tv_profile_menu_notification_settings -> {
             }*/
            R.id.tv_profile_menu_terms -> {
            }
            R.id.tv_profile_menu_privacy_policy -> {
            }
            R.id.tv_user_profile_edit -> {

                showProfileEditView()

            }
            R.id.tv_user_profile_logout -> {

                profileViewModel.logoutFromServer()
            }
            R.id.tv_user_profile_delete_Account -> {


                val clickListener = DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    profileViewModel.deleteUserProfile()
                }

                UiUtils.showAlertDialog(
                    activity,
                    "Delete",
                    "Do you really want to delete your account?",
                    positiveText = "DELETE",
                    negativeText = "CANCEL",
                    positiveClickListener = clickListener,
                    cancelable = true,
                    showNegativeButton = true
                )


            }


            R.id.tv_change_picture -> {

                val bottomSheetDialogChangeRemoveUserImage =
                    BottomSheetDialogChangeRemoveUserImage()
                bottomSheetDialogChangeRemoveUserImage.show(
                    childFragmentManager,
                    "BottomSheetDialogChangeRemoveUserImage"
                )

            }

            R.id.tv_save_changes -> {

                callApiToUpdateUser()

            }
        }
    }

    private fun localLogoutAndGoHome() {
        profileViewModel.logoutPref()

        activity?.let {
            if (it is HomeActivity) {
                it.gotToHome()
            }
        }

    }

    private fun callApiToUpdateUser() {

        val validateNameError = AndroidUtils.validateName(edt_name.text.toString())
        val validateEmailError = AndroidUtils.validateEmail(edt_email.text.toString())

        if (
            TextUtils.isEmpty(validateNameError)
            && TextUtils.isEmpty(validateEmailError)
        ) {


            profileViewModel.updateUserProfileAPI(
                edt_name.text.toString().trim(),
                edt_email.text.toString().trim(),
                imageToUpdate
            )


        } else {
            edt_name.error = validateNameError
            edt_email.error = validateEmailError
        }

    }


    override fun removePicture() {

        profileViewModel.removeUserImage()

    }

    override fun uploadPicture() {

        context?.let { CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(it, this) }

    }


    private var mealFacts: DataMealFacts? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {


                imageToUpdate = result?.uri?.toString()
                ImageRequestManager.with(sv_user_image_edit)
                    .setPlaceholderImage(R.drawable.placeholder_image_avatar)
                    .url(imageToUpdate)
                    .circular(true)
                    .build()

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                UiUtils.showToast(activity, "Cropping failed: " + result.error)
            }
        } else if (requestCode == ActivitySelectMealConsume.REQUEST_CODE_MULTIPLE_MEAL_CONSUMED) {

            if (resultCode == RESULT_OK) {
                mealFacts =
                        Parcels.unwrap<DataMealFacts>(data?.getParcelableExtra<Parcelable>(ActivitySelectMealConsume.KEY_SAVED_DATA_ON_MEAL_CONSUMPTION))
                var earnedOnThisConsumption =
                    data?.getIntExtra(ActivitySelectMealConsume.KEY_POINT_EARNED_ON_MEAL_CONSUMPTION, -1)

                var totalEarnedPoints = mealFacts?.user?.earned_points
                totalEarnedPoints?.let {
                    profileViewModel.setEarnedPoints(it)
                    setEarnedPoints()
                }
                showEarnedPointsBS(earnedOnThisConsumption)
            }

        }
    }


    private fun showProfileEditView() {
        content_user_profile_info.visibility = View.GONE
        content_edit_profile.visibility = View.VISIBLE


        edt_name?.setText(profileViewModel.getUserName())
        edt_email?.setText(profileViewModel.getUserEmail())

        ImageRequestManager.with(sv_user_image_edit)
            .setPlaceholderImage(R.drawable.placeholder_image_avatar)
            .url(profileViewModel.getUserImage())
            .circular(true)
            .build()

    }

    private fun showProfileInfoView() {
        content_user_profile_info.visibility = View.VISIBLE
        content_edit_profile.visibility = View.GONE
    }

    fun canCloseScreen(): Boolean {

        if (content_edit_profile.visibility == View.VISIBLE) {
            showProfileInfoView()

            return false
        }

        return true


    }

    companion object {

        fun getInstance(): FragmentUserProfile {
            val bundle = Bundle()

            val fragment = FragmentUserProfile()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun showLoadingView() {

        showProgressDialog(null, AndroidUtils.getString(R.string.loading))
    }

    override fun <T> showErrorView(response: RestResponse<T>?, imageResource: Int) {

        hideProgressDialog()
        showSnackbar(response?.getErrorMessage(), false)

    }

    override fun showEmptyView(title: String?, message: String?, imageResource: Int) {
        hideProgressDialog()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        Log.i("FRAG_FLOW", "onActivityCreated, SubScribeVieModel")

        setUpUi()




        subscribeUi()
        loadData()
    }


    var earnedOnThisSessionConsumption: Int? = 0

    private fun showEarnedPointsBS(earnedOnThisConsumption: Int?) {

        this.earnedOnThisSessionConsumption = earnedOnThisConsumption

        val bottomSheetDialogPointsEarnedAfterEnterMeal =
            BottomSheetDialogPointsEarnedAfterEnterMeal()
        bottomSheetDialogPointsEarnedAfterEnterMeal.show(
            childFragmentManager,
            "BottomSheetDialogPointsEarnedAfterEnterMeal"
        )
    }


    fun openSaveContentPage() {


        if (mealFacts != null && !mealFacts?.meal_facts.isNullOrEmpty()) {

            activity?.let {
                startActivity(ActivityWhatYouSaved.getIntent(it, mealFacts))
            }
        }


    }


//    override fun onStart() {
//        super.onStart()
//        Log.i("FRAG_FLOW", "onStart")
//
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.i("FRAG_FLOW", "onStop")
//
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Log.i("FRAG_FLOW", "onCreate")
//
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Log.i("FRAG_FLOW", "onCreateView")
//        return super.onCreateView(inflater, container, savedInstanceState)
//
//    }
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        Log.i("FRAG_FLOW", "onViewCreated")
//
//    }
//
//
//    override fun onAttachFragment(childFragment: Fragment?) {
//        super.onAttachFragment(childFragment)
//        Log.i("FRAG_FLOW", "onAttachFragment")
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.i("FRAG_FLOW", "onDestroy")
//
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.i("FRAG_FLOW", "onDestroyView")
//
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        Log.i("FRAG_FLOW", "onDetach")
//
//    }
//
//    override fun onPause() {
//        super.onPause()
//        Log.i("FRAG_FLOW", "onPause")
//
//    }
//
//    override fun onResume() {
//        super.onResume()
//        Log.i("FRAG_FLOW", "onResume")
//
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        Log.i("FRAG_FLOW", "onSaveInstanceState")
//
//    }
//
//    override fun onViewStateRestored(savedInstanceState: Bundle?) {
//        super.onViewStateRestored(savedInstanceState)
//        Log.i("FRAG_FLOW", "onViewStateRestored")
//
//    }


}
