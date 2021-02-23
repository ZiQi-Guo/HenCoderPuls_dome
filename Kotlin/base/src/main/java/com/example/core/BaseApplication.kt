package com.example.core

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        currentApplication = this
    }

    companion object {
        @JvmStatic
        lateinit var currentApplication: Context
            private set
    }
}