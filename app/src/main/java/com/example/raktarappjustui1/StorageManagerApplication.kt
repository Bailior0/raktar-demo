package com.example.raktarappjustui1

import android.app.Application
import co.zsmb.rainbowcake.config.rainbowCake
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class StorageManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        rainbowCake {
            isDebug = BuildConfig.DEBUG
        }
    }
}