package com.martial.beemr.utils

import android.content.Context

object AppPreference {


    private const val SHARED_PREF = "com.sampletaskmathon.mathongoassignment.shared_pref"

    //PREFERENCE VARIABLES
    const val USER_EMAIL                = "com.chimtaa.userEmail"

    fun clearPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    fun saveToSharedPrefString(context: Context, key: String, value: String?) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value).apply()
    }

    fun fetchSharedPrefString(context: Context, key: String): String? {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val value = sharedPreferences.getString(key, null)
        return value
    }
}