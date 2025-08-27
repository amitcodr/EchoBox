package com.github.amitcodr.core.utils

import android.content.Context
import android.os.Build
import androidx.core.content.pm.PackageInfoCompat

object AppInfoUtils {
    fun getAppMetadata(context: Context): Map<String, String> {
        val packageManager = context.packageManager
        val packageName = context.packageName

        val versionName = try {
            val info = packageManager.getPackageInfo(packageName, 0)
            info.versionName ?: "N/A"
        } catch (e: Exception) {
            "N/A"
        }

        val versionCode = try {
            val info = packageManager.getPackageInfo(packageName, 0)
            PackageInfoCompat.getLongVersionCode(info).toString()
        } catch (_: Exception) {
            "N/A"
        }

        return mapOf(
            "App Version" to "$versionName ($versionCode)",
            "Device Model" to "${Build.MANUFACTURER} ${Build.MODEL}",
            "Android Version" to Build.VERSION.RELEASE,
            "SDK Level" to Build.VERSION.SDK_INT.toString()
        )
    }
}