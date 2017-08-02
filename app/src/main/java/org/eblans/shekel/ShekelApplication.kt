package org.eblans.shekel

import android.app.Application
import android.os.StrictMode

class ShekelApplication : Application() {
    override fun onCreate() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)
        super.onCreate()
    }
}