package com.villageapp.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.villageapp.R
import com.villageapp.base.BaseActivity
import com.villageapp.base.BaseApplication


class UiUtils {

    companion object {

        private var snackbar: Snackbar? = null
        private var toast: Toast? = null
        private var progressDialog: ProgressDialog? = null
        private var alertDialog: AlertDialog? = null

        fun loadImage(@DrawableRes id: Int, imageView: ImageView?) {
            try {
                imageView?.setImageResource(id)
            } catch (e: Throwable) {
                LogUtils.e(e)
            }
        }

        fun setPasswordFont(editText: EditText?) {
            editText?.typeface = Typeface.DEFAULT
        }

        fun showSnackBar(context: Context?, msg: String?, positive: Boolean) {
            if (context is BaseActivity) {
                val view: View? = context.findViewById(android.R.id.content)
                showSnackBar(view, msg, positive)
            }
        }

        fun showSnackBar(view: View?, msg: String?, positive: Boolean) {
            if (!isEmpty(msg) && view != null) {
                hideSnackBar()

                snackbar = Snackbar.make(view, msg!!, Snackbar.LENGTH_SHORT)
                val snackView = snackbar?.view
                if (positive) {
                    snackView?.setBackgroundColor(AndroidUtils.getColor(R.color.snackbar_green_bg))
                } else {
                    snackView?.setBackgroundColor(AndroidUtils.getColor(R.color.snackbar_red_bg))
                }

                val tv: TextView? = snackView?.findViewById(android.support.design.R.id.snackbar_text)

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    tv?.setTextAlignment(View.TEXT_ALIGNMENT_CENTER)
                } else
                    tv?.setGravity(Gravity.CENTER_HORIZONTAL)

                snackbar?.show()
            }
        }

        fun hideSnackBar() {
            try {
                snackbar?.dismiss()
            } catch (e: Exception) {
                LogUtils.e(e)
            }
        }

        fun showToast(context: Context?, message: String?) {
            if (context != null && !isEmpty(message)) {
                hideToast()

                toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                toast?.show()
            }
        }

        fun hideToast() {
            toast?.cancel()
        }

        fun showProgressDialog(context: Context?, title: String?, message: String?, cancelable: Boolean) {
            try {
                dismissProgressDialog()

                if (context == null) {
                    return
                }

                progressDialog = ProgressDialog.show(
                        context,
                        title,
                        message,
                        true,
                        cancelable)
            } catch (e: Exception) {
                LogUtils.e(e)
            }

        }

        fun dismissProgressDialog() {
            try {
                if (progressDialog?.isShowing == true) {
                    progressDialog?.dismiss()
                }
            } catch (e: Exception) {
                LogUtils.e(e)
            } finally {
                progressDialog = null
            }
        }

        fun showAlertDialog(context: Context?,
                            title: String?,
                            message: String?,
                            positiveText: String = "OK",
                            positiveClickListener: DialogInterface.OnClickListener? = null,
                            showNegativeButton: Boolean = false,
                            negativeText: String = "CANCEL",
                            negativeClickListener: DialogInterface.OnClickListener? = null,
                            cancelable: Boolean = false) {
            try {
                hideAlertDialog()

                val builder = AlertDialog.Builder(context ?: return)

                if (!isEmpty(title)) {
                    builder.setTitle(title)
                }

                builder.setMessage(message)

                if (positiveClickListener != null) {
                    builder.setPositiveButton(positiveText, positiveClickListener)
                } else {
                    builder.setPositiveButton(positiveText) { dialog, which ->
                        hideAlertDialog()
                        hideAlertDialog(dialog)
                    }
                }

                if (showNegativeButton || negativeClickListener != null) {
                    if (negativeClickListener != null) {
                        builder.setNegativeButton(negativeText, negativeClickListener)
                    } else {
                        builder.setNegativeButton(negativeText) { dialog, which ->
                            hideAlertDialog()
                            hideAlertDialog(dialog)
                        }
                    }
                }

                builder.setCancelable(cancelable)

                alertDialog = builder.create()
                alertDialog?.show()
            } catch (e: Exception) {
                LogUtils.e(e)
            }
        }

        private fun hideAlertDialog(dialog: DialogInterface?) {
            try {
                dialog?.dismiss()
            } catch (e: Exception) {
                // ignore
            }
        }

        fun hideAlertDialog() {
            try {
                alertDialog?.dismiss()
            } catch (e: Exception) {
                LogUtils.e(e)
            } finally {
                alertDialog = null
            }
        }

        fun getDeviceWidth(): Int {
            try {
                return BaseApplication.getInstance().resources.displayMetrics.widthPixels
            } catch (e: Exception) {
                LogUtils.e(e)
            }
            return 0
        }

        fun getDeviceHeight(): Int {
            try {
                return BaseApplication.getInstance().resources.displayMetrics.heightPixels
            } catch (e: Exception) {
                LogUtils.e(e)
            }
            return 0
        }

        fun getStatusBarHeight(context: Context): Int {
            var result = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

        fun hideSoftKeyboard(view: View) {
            try {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE)
                if (imm is InputMethodManager) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            } catch (e: Exception) {
                LogUtils.e(e)
            }

        }

        fun hideSoftKeyboard(context: Activity) {
            try {
                val v = context.window.currentFocus
                if (v != null) {
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                    if (imm is InputMethodManager) {
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                } else {
                    try {
                        // TODO handle hide keyboard when view is null
                    } catch (e: Exception) {
                        LogUtils.e(e)
                    }

                }
            } catch (e: Exception) {
                LogUtils.e(e)
            }

        }

        fun showSoftKeyboard(context: Activity, editText: EditText) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                if (imm is InputMethodManager) {
                    imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
                }
            } catch (e: Exception) {
                LogUtils.e(e)
            }

        }

        fun dpToPx(dp: Float): Float {
            return dp * getDeviceDensity()
        }

        fun getDeviceDensity(): Float {
            return Resources.getSystem().getDisplayMetrics().density
        }

        fun hideViews(vararg views: View) {
            if (views != null) {
                for (view in views) {
                    if (view != null) {
                        view.visibility = View.GONE
                    }
                }
            }
        }

        fun showViews(vararg views: View) {
            if (views != null) {
                for (view in views) {
                    if (view != null) {
                        view.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}