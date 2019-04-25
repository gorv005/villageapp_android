package com.villageapp.utils

import android.net.Uri
import android.widget.EditText

fun isEmpty(string: String?): Boolean = string?.trim()?.isEmpty() ?: true

fun isEmpty(uri: Uri?) = isEmpty(uri?.toString())

fun isEmpty(editText: EditText?) = isEmpty(editText?.text?.toString())

fun isEmpty(list: List<Any>?) = list?.isEmpty() ?: true

fun compareString(str1: String?, str2: String?, ignoreCase: Boolean = true) = str1?.equals(str2, ignoreCase)
        ?: false