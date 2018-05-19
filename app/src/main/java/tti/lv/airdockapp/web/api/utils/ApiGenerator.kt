package tti.lv.airdockapp.web.api.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import tti.lv.airdockapp.utilities.SharedPreferenceProvider


/**
 * https://futurestud.io/tutorials/retrofit-token-authentication-on-android
 */
class ApiGenerator (
        val sharedPreferenceProvider: SharedPreferenceProvider
){
    val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()

    val builder = Retrofit.Builder()
            .baseUrl("http://213.219.36.14:8080")
            .addConverterFactory(createCustomGsonFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun <C> createService(serviceClasss : Class<C>, shouldAddToken : Boolean = true) : C {

        if (shouldAddToken) {
            val interceptor = AuthenticationInterceptor(sharedPreferenceProvider)

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
            }

            val retrofitBuilder = builder.client(httpClient.build())
            return retrofitBuilder.build().create(serviceClasss)

        }

        return builder.build().create(serviceClasss)
    }

    private fun createCustomGsonFactory() =
            GsonConverterFactory.create(
                    GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                            .create()
            )

}