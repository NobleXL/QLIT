package com.noble.qlit

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @author: noble
 * @desc: MyApp 获取当前context
 */
class MyApp : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}