package tti.lv.airdockapp.di

import dagger.Component
import tti.lv.airdockapp.MainActivity

@Component
interface AppComponent {
    fun inject(app : MainActivity)
}