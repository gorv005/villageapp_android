package com.villageapp.utils

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

open class CustomClickableSpan : ClickableSpan() {

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.isUnderlineText = false
    }

    override fun onClick(view: View) {
        // empty
    }
}