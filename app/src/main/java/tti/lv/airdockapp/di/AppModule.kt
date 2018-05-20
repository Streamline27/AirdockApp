package tti.lv.airdockapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import tti.lv.airdockapp.web.api.utils.ApiGenerator
import tti.lv.airdockapp.utilities.SharedPreferenceProvider
import tti.lv.airdockapp.web.api.AuthorizationApi
import tti.lv.airdockapp.web.api.RequestApi
import tti.lv.airdockapp.web.api.TaskApi
import javax.inject.Singleton

@Module
class AppModule(private val ctx : Context) {

    @Singleton @Provides fun context() = ctx

    @Singleton @Provides fun sharedPreferencesProvider(ctx : Context) = SharedPreferenceProvider(ctx)
    @Singleton @Provides fun getApiGenerator(provider : SharedPreferenceProvider) = ApiGenerator(provider)

    @Singleton @Provides
    fun authorizationApi(generator: ApiGenerator) =
            generator.createService(
                    serviceClasss  = AuthorizationApi::class.java,
                    shouldAddToken = false
            )

    @Singleton @Provides
    fun taskApi(generator: ApiGenerator) = generator.createService(TaskApi::class.java)

    @Singleton @Provides
    fun requestApi(generator: ApiGenerator) = generator.createService(RequestApi::class.java)
}