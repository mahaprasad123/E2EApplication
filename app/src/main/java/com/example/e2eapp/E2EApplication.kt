package com.example.e2eapp

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class E2EApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Log.d("MPC","E2EApplication - onCreate")
    }
}