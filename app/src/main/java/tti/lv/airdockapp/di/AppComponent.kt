package tti.lv.airdockapp.di

import dagger.Component
import tti.lv.airdockapp.screens.AppActivity
import tti.lv.airdockapp.screens.login.LoginActivity
import tti.lv.airdockapp.screens.main.MainActivity
import tti.lv.airdockapp.screens.main.tasks.parts.TaskDetailsFragment
import tti.lv.airdockapp.screens.main.tasks.parts.TaskHistoryFragment
import tti.lv.airdockapp.screens.main.tasks.parts.TaskListFragment
import tti.lv.airdockapp.screens.main.tasks.TasksFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(target : MainActivity)
    fun inject(target : AppActivity)
    fun inject(target : LoginActivity)

    fun inject(target : TasksFragment)
    fun inject(target : TaskListFragment)
    fun inject(target : TaskHistoryFragment)
    fun inject(target : TaskDetailsFragment)
}