package com.villageapp.ui.user.register

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.facebook.*
import com.facebook.common.util.UriUtil
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.villageapp.R
import com.villageapp.base.BaseFragment
import com.villageapp.callbacks.SignUpFragmentInterface
import com.villageapp.managers.ImageRequestManager
import com.villageapp.models.signUp.ResponseSignUp
import com.villageapp.models.signin.ResponseSignIn
import com.villageapp.network.RestResponse
import com.villageapp.ui.user.password.forgot.ActivityForgotPassword
import com.villageapp.utils.*
import kotlinx.android.synthetic.main.base_common_layout_signup_module.*
import kotlinx.android.synthetic.main.content_login_signup.*
import kotlinx.android.synthetic.main.view_bottom_sign_in.*
import kotlinx.android.synthetic.main.view_bottom_sign_up.*
import kotlinx.android.synthetic.main.view_email.*
import kotlinx.android.synthetic.main.view_login_sign_up_skip.*
import kotlinx.android.synthetic.main.view_name.*
import org.koin.android.architecture.ext.viewModel
import java.util.*


class FragmentLoginSignUp : BaseFragment() {

    private val viewModel: LoginSignUpViewModel by viewModel()


    override fun getLayoutId() = R.layout.base_common_layout_signup_module

    private var isLoginScreen: Boolean? = false

    private var signUpFragmentInterface: SignUpFragmentInterface? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is SignUpFragmentInterface) {
            signUpFragmentInterface = context
        }
    }

    private var viewContentSignUpSignIn: View? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            val nullParent = null

            viewContentSignUpSignIn = LayoutInflater.from(activity).inflate(R.layout.content_login_signup, nullParent)
            fl_container_add_content.addView(viewContentSignUpSignIn)
        }

        ImageRequestManager.with(login_bg_image)
            .uri(UriUtil.getUriForResourceId(R.drawable.bg_signup))
            .build()



        isLoginScreen = arguments?.getBoolean(KEY_IS_LOGIN)

        if (isLoginScreen != null && isLoginScreen as Boolean) {

            setUpUiLogIn()
        } else {
            setUpUiSignUp()
        }

        tv_login_signup_skip.setOnClickListener {
            signUpFragmentInterface?.onSkipClick()
        }

        subscribeUiLogin()
        subscribeUiSignUp()
        subscribeUiFBLogin()

    }


    private fun subscribeUiLogin() {
        viewModel.eventSignInUser.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataAfterLogin(it.data)
            }
        })
    }

    private fun subscribeUiFBLogin() {
        viewModel.eventFBSignInUser.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> {

                    it.data?.result?.isFBLogin = true
                    showDataAfterLogin(it.data)

                }
            }
        })
    }

    private fun subscribeUiSignUp() {
        viewModel.eventSignUpUser.observe(this, Observer {
            when (it?.status) {
                RestResponse.Status.LOADING -> showLoadingView()

                RestResponse.Status.ERROR -> showErrorView(it)

                RestResponse.Status.SUCCESS -> showDataAfterSignUp(it.data)
            }
        })
    }


    fun setUpUiLogIn() {

        tv_name_title.visibility = View.GONE
        edt_name.visibility = View.GONE

        ll_bottom_sign_in.visibility = View.VISIBLE
        ll_bottom_sign_up.visibility = View.GONE

        tv_sign_up_in_sign_in.setOnClickListener {
            signUpFragmentInterface?.setSignUpFragment()
        }


        tv_login_signup_skip.visibility = View.VISIBLE
        tv_sign_in_forgot_pwd.setOnClickListener {

            activity?.let { it1 -> startActivity(ActivityForgotPassword.getIntent(it1)) }
        }

        tv_signin_for_sign_in.setOnClickListener {
            doSignIn()
        }

    }

    fun setUpUiSignUp() {

        tv_name_title.visibility = View.VISIBLE
        edt_name.visibility = View.VISIBLE

        ll_bottom_sign_in.visibility = View.GONE
        ll_bottom_sign_up.visibility = View.VISIBLE

        tv_sign_in_from_sign_up.setOnClickListener {
            signUpFragmentInterface?.setSignInFragment()

        }

        setTextViewHTML(tv_sign_up_terms, AndroidUtils.getString(R.string.signup_terms_and_conditions))

        tv_sign_up.setOnClickListener {
            doSignUp()
        }

        tv_sign_up_facebook.setOnClickListener {
            doFbLogin()
        }


    }

    private var callbackManager: CallbackManager? = null

    private fun doFbLogin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    // App code

                    fbSignIn(loginResult.accessToken?.token)
                }

                override fun onCancel() {
                    // App code

                    UiUtils.showSnackBar(activity, "Login Canceled", false)

                }

                override fun onError(exception: FacebookException) {
                    // App code

                    UiUtils.showSnackBar(activity, exception.message, false)
                }
            })

//        val accessToken = AccessToken.getCurrentAccessToken()
//        val isLoggedIn = accessToken != null && !accessToken.isExpired
//
//        if (!isLoggedIn)
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"))
    }

    private fun setTextViewHTML(text: TextView, html: String) {
        val sequence = AndroidUtils.getHtmlText(html) ?: return
        val strBuilder = SpannableStringBuilder(sequence)
        val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
        for (span in urls) {
            makeLinkClickable(strBuilder, span)
        }
        text.text = strBuilder
        text.movementMethod = LinkMovementMethod.getInstance()
    }


    private fun makeLinkClickable(strBuilder: SpannableStringBuilder, span: URLSpan) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        val clickable = object : CustomClickableSpan() {
            override fun onClick(view: View) {
                LogUtils.d("URL clicked", span.url)

                if (!isEmpty(span.url) && span.url.toLowerCase().contains("Terms & Conditions")) {
//                    startActivity(InformationActivity.getIntent(context, "Privacy Policy", R.string.url_privacy_policy))
                    activity?.finish()
                } else if (!isEmpty(span.url) && span.url.toLowerCase().contains("Privacy Policy")) {
//                    startActivity(InformationActivity.getIntent(context, "Privacy Policy", R.string.url_terms_and_conditions))
                    activity?.finish()
                }
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

    companion object {


        const val KEY_IS_LOGIN = "KEY_IS_LOGIN"

        const val GRANT_TYPE_PASSWORD = "password"
        const val GRANT_TYPE_FACEBOOK = "assertion"
        const val PROVIDER_FACEBOOK = "facebook"

        fun getInstance(isLoginScreen: Boolean): FragmentLoginSignUp {
            val bundle = Bundle()

            bundle.putBoolean(KEY_IS_LOGIN, isLoginScreen)
            val fragment = FragmentLoginSignUp()

            fragment.arguments = bundle

            return fragment
        }
    }

    fun doSignUp() {

        activity?.let { UiUtils.hideSoftKeyboard(it) }

        val validateNameError = AndroidUtils.validateName(edt_name.text.toString())
        val validateEmailError = AndroidUtils.validateEmail(edt_email.text.toString())
        val validatePasswordError = AndroidUtils.validatePassword(edt_pwd.text.toString())
        val termsAccepted =
            if (!cb_sign_up_terms.isChecked) AndroidUtils.getString(R.string.error_terms_not_accepted) else null

        if (
            TextUtils.isEmpty(validateNameError)
            && TextUtils.isEmpty(validateEmailError)
            && TextUtils.isEmpty(validatePasswordError)
        ) {
            //Do Sign up


            if (TextUtils.isEmpty(termsAccepted)) {

                viewModel.signUpUser(
                    edt_email.text.toString().trim(),
                    edt_name.text.toString().trim(),
                    edt_pwd.text.toString()
                )
            } else {
                showSnackbar(termsAccepted, false)
            }


        } else {
            edt_name.error = validateNameError
            edt_email.error = validateEmailError
            edt_pwd.error = validatePasswordError
        }


    }

    fun doSignIn() {

        activity?.let { UiUtils.hideSoftKeyboard(it) }

        val validateEmailError = AndroidUtils.validateEmail(edt_email.text.toString())
        val validatePasswordError = AndroidUtils.validatePassword(edt_pwd.text.toString())

        if (
            TextUtils.isEmpty(validateEmailError)
            && TextUtils.isEmpty(validatePasswordError)
        ) {
            //Do Sign up


            viewModel.signInUser(
                edt_email.text.toString().trim(),
                GRANT_TYPE_PASSWORD,
                edt_pwd.text.toString(),
                cb_sign_in_remember_me.isChecked
            )


        } else {
            edt_email.error = validateEmailError
            edt_pwd.error = validatePasswordError
        }


    }


    fun fbSignIn(assertionFbToken: String?) {

        activity?.let { UiUtils.hideSoftKeyboard(it) }

        assertionFbToken?.let {
            viewModel.facebookLogin(
                PROVIDER_FACEBOOK,
                it,
                GRANT_TYPE_FACEBOOK
            )
        }


    }


    private fun showDataAfterLogin(data: ResponseSignIn?) {

        hideProgressDialog()
        data?.let { saveLoggedInUserDataToPref(it) }

        signUpFragmentInterface?.onSignInSuccessful(data)
    }

    private fun saveLoggedInUserDataToPref(data: ResponseSignUp) {

        viewModel.repository.saveLoggedInUserDataToPref(data)
    }

    private fun saveLoggedInUserDataToPref(data: ResponseSignIn) {

        viewModel.repository.saveLoggedInUserDataToPref(data)
    }

    private fun showDataAfterSignUp(data: ResponseSignUp?) {
        hideProgressDialog()
        data?.let { saveLoggedInUserDataToPref(it) }

        signUpFragmentInterface?.onSignUpSuccessful(data)

    }


    override fun showLoadingView() {

        showProgressDialog(null, AndroidUtils.getString(R.string.please_wait))
    }

    override fun <T> showErrorView(response: RestResponse<T>?, imageResource: Int) {

        hideProgressDialog()

        showSnackbar(response?.getErrorMessage(), false)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun setFacebookData(loginResult: LoginResult) {
        val request = GraphRequest.newMeRequest(
            loginResult.accessToken
        ) { _, response ->
            // Application code
            try {
                Log.i("Response", response.toString())

                val email = response.jsonObject.optString("email")
                val firstName = response.jsonObject.optString("first_name")
                val lastName = response.jsonObject.optString("last_name")
                val gender = response.jsonObject.optString("gender", null)


                val profile = Profile.getCurrentProfile()
                val link = profile?.linkUri?.toString()
                Log.i("Link", link)
                if (Profile.getCurrentProfile() != null) {
                    Log.i("Login", "ProfilePic" + Profile.getCurrentProfile()?.getProfilePictureUri(200, 200))
                }

                Log.i("Login" + "Email", email)
                Log.i("Login" + "FirstName", firstName)
                Log.i("Login" + "LastName", lastName)
                Log.i("Login" + "Gender", gender)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,email,first_name,last_name,gender")
        request.parameters = parameters
        request.executeAsync()
    }
}