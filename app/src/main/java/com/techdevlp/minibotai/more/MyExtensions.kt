package com.techdevlp.minibotai.more

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.TypedValue
import android.view.View
import android.view.Window
import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.techdevlp.minibotai.more.REQUEST_CODE_UPDATE

@Composable
fun SetStatusBarColor(color: Color, isIconLight: Boolean) {
    val view: View = LocalView.current
    SideEffect {
        val window: Window = (view.context as Activity).window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isIconLight
    }
}

@Composable
fun SetNavigationBarColor(color: Color, isIconLight: Boolean) {
    val view: View = LocalView.current
    SideEffect {
        val window: Window = (view.context as Activity).window
        window.navigationBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = isIconLight
    }
}

@Composable
fun spSizeResource(@DimenRes id: Int): TextUnit {
    val resources: Resources = LocalContext.current.resources

    return with(TypedValue()) {
        resources.getValue(id, this, true)
        if (type == TypedValue.TYPE_DIMENSION && complexUnit == TypedValue.COMPLEX_UNIT_SP) {
            val scaledValue = TypedValue.complexToFloat(data)
            scaledValue.sp
        } else {
            error("Dimension resource $id is not of type COMPLEX_UNIT_SP.")
        }
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val result: Boolean
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}

fun navigateToRateUs(activity: Activity, packageName:String) {
    try {
        activity.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
        )
    } catch (e: android.content.ActivityNotFoundException) {
        activity.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

@Composable
fun CheckForUpdates(appUpdateManager: AppUpdateManager) {
    val activity = LocalContext.current as Activity
    appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
        ) {
            // Request immediate app update
            appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                AppUpdateType.IMMEDIATE,
                activity,
                REQUEST_CODE_UPDATE
            )
        }
    }
}

fun showToast(context: Context, message: String) {
    val toast = android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT)
    toast.show()
}