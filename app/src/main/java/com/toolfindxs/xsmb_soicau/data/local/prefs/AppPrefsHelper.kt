package com.toolfindxs.xsmb_soicau.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.toolfindxs.xsmb_soicau.utils.Const

class AppPrefsHelper(context: Context, prefsName: String) : PrefsHelper {
    companion object {
        private const val COIN = "COIN"
        private const val RESULT_SAMPLE = "RESULT_SAMPLE"
        private const val RESULT_VIP = "RESULT_VIP"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override var coin: Int
        get() = sharedPreferences.getInt(COIN, Const.COIN_DEFAULT)
        set(value) {
            sharedPreferences.edit().putInt(COIN, value).apply()
        }
    override var resultSample: String
        get() = sharedPreferences.getString(RESULT_SAMPLE, Const.VALUE_DEFAULT)?: Const.VALUE_DEFAULT
        set(value) {
            sharedPreferences.edit().putString(RESULT_SAMPLE, value).apply()
        }

    override var resultVip: String
        get() = sharedPreferences.getString(RESULT_VIP, Const.VALUE_DEFAULT)?: Const.VALUE_DEFAULT
        set(value) {
            sharedPreferences.edit().putString(RESULT_VIP, value).apply()
        }
}