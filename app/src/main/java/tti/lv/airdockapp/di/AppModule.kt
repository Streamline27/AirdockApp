package tti.lv.airdockapp.di

import dagger.Module
import dagger.Provides
import tti.lv.airdockapp.Info

@Module
class AppModule {

    @Provides fun getInfo() = Info()
}