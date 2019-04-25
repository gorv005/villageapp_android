package com.villageapp.managers.prefrence

import android.content.Context
import com.villageapp.utils.Config

open class BasePrefManager(context: Context)
{

     val sharedPreferences = context.getSharedPreferences(
        Config.SharedPreferences.PROPERTY_PREF,
        Context.MODE_PRIVATE
    )

    fun savePreference(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun savePreference(key: String, value: Int?, defaultValue: Int = 0) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value ?: defaultValue)
        editor.apply()
    }

    fun savePreference(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringPreference(prefName: String, defaultValue: String? = null): String? =
        sharedPreferences.getString(prefName, defaultValue)

    fun getIntPreference(prefName: String, defaultValue: Int = 0) = sharedPreferences.getInt(prefName, defaultValue)

    fun getBooleanPreference(prefName: String, defaultValue: Boolean = false) =
        sharedPreferences.getBoolean(prefName, defaultValue)

    private fun clearPreference(key: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, null)
        editor.apply()
    }

}