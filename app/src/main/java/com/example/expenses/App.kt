package com.example.expenses

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun newImageLoader(): ImageLoader {
//        Timber.tag("test").d("i am called")
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }

//    override fun onCreate() {
//        super.onCreate()
//        if (BuildConfig.DEBUG) {
//            Timber.plant(DebugTree())
//        } else
//            Timber.plant(ReleaseTree())
//
//    }
}