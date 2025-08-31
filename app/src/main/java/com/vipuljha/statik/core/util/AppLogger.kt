package com.vipuljha.statik.core.util


import android.util.Log
import com.vipuljha.statik.BuildConfig

object AppLogger {

    fun d(tag: String, message: String, forceLog: Boolean = false) {
        if (BuildConfig.DEBUG || forceLog) {
            Log.d(tag, message)
        }
    }

    fun i(tag: String, message: String, forceLog: Boolean = false) {
        if (BuildConfig.DEBUG || forceLog) {
            Log.i(tag, message)
        }
    }

    fun w(tag: String, message: String, forceLog: Boolean = false) {
        if (BuildConfig.DEBUG || forceLog) {
            Log.w(tag, message)
        }
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
        //TODO: Forward to Crashlytics or Sentry
    }
}
