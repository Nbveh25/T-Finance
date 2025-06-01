package ru.practice.t_finance

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TFinanceApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}