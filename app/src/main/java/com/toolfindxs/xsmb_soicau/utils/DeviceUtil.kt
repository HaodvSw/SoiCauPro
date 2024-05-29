package com.toolfindxs.xsmb_soicau.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.view.inputmethod.InputMethodManager

fun getTextFromHtml(html: String): String {
    var text = ""
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        text = Html.fromHtml(html).toString()
    }
    return text
}

fun showDialogSetting(context: Context) {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    context.startActivity(intent)
}

fun showSoftKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun hiddenSoftKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
}

fun hasNetworkConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        )
            return true
    }
    return false
}

fun screenWidth(): Int {
    return Resources.getSystem().getDisplayMetrics().widthPixels
}

fun screenHeight(): Int {
    return Resources.getSystem().getDisplayMetrics().heightPixels
}

//val deviceId
//    @SuppressLint("HardwareIds")
//    get() = Settings.Secure.getString(
//        MainApp.getInstance().applicationContext!!.contentResolver,
//        Settings.Secure.ANDROID_ID
//    )