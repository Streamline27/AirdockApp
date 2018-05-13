package tti.lv.airdockapp.di

import dagger.Component
import tti.lv.airdockapp.screens.login.LoginActivity
import tti.lv.airdockapp.screens.main.MainActivity
import tti.lv.airdockapp.screens.main.tasks.TaskListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app : MainActivity)
    fun inject(app : LoginActivity)
    fun inject(app : TaskListFragment)
}