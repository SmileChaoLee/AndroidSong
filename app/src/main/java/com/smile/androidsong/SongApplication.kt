package com.smile.androidsong

import android.app.Application
import android.util.Log
import com.smile.androidsong.interfaces.DaggerSongAppComponent
import com.smile.androidsong.interfaces.SongAppComponent

class SongApplication : Application() {
    companion object {
        private const val TAG = "SongApplication"
        val appComponent : SongAppComponent = DaggerSongAppComponent.create()
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate")
        super.onCreate()
    }

    override fun onTrimMemory(level: Int) {
        Log.d(TAG, "onTrimMemory")
        super.onTrimMemory(level)
    }
}