package com.mzaimilzam.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Muhammad Zaim Milzam on 03/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */

@HiltAndroidApp
class PokedexApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}