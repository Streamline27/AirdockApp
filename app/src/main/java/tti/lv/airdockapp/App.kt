package tti.lv.airdockapp

import android.app.Application
import tti.lv.airdockapp.di.AppComponent
import tti.lv.airdockapp.di.AppModule
import tti.lv.airdockapp.di.DaggerAppComponent

class App : Application() {

    lateinit var dependencyGraph: AppComponent

    override fun onCreate() {
        super.onCreate()

        dependencyGraph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}